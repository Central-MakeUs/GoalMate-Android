package cmc.goalmate.remote.interceptor

import cmc.goalmate.data.util.getOrThrow
import cmc.goalmate.local.TokenDataStore
import cmc.goalmate.remote.dto.request.RefreshRequest
import cmc.goalmate.remote.dto.response.TokenResponse
import cmc.goalmate.remote.service.AuthService
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class AuthAuthenticator
    @Inject
    constructor(
        private val authService: AuthService,
        private val tokenDataStore: TokenDataStore,
    ) : Authenticator {
        override fun authenticate(
            route: Route?,
            response: Response,
        ): Request? {
            val refreshToken =
                runBlocking {
                    tokenDataStore.refreshToken.first()
                }

            if (refreshToken.isBlank()) {
                // 토큰이 비어있을 경우
                return null
            }
            return runBlocking {
                refresh(refreshToken).fold(
                    onSuccess = {
                        tokenDataStore.saveAccessToken(it.accessToken)
                        tokenDataStore.saveRefreshToken(it.refreshToken)
                        AuthorizationInterceptor.addBearerTokenToRequest(
                            response.request,
                            it.accessToken,
                        )
                    },
                    onFailure = {
                        null
                    },
                )
            }
        }

        private suspend fun refresh(refreshToken: String): Result<TokenResponse> =
            runCatching {
                val request = RefreshRequest(refreshToken = refreshToken)
                authService.postRefresh(request = request).getOrThrow()
            }
    }
