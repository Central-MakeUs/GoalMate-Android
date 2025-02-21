package cmc.goalmate.data.datasource

import cmc.goalmate.data.model.LoginDto
import cmc.goalmate.data.model.TokenDto
import cmc.goalmate.data.model.toData
import cmc.goalmate.data.util.getOrThrow
import cmc.goalmate.remote.dto.request.LoginRequest
import cmc.goalmate.remote.dto.request.RefreshRequest
import cmc.goalmate.remote.service.AuthService
import javax.inject.Inject

class AuthDataSource
    @Inject
    constructor(private val authService: AuthService) {
        suspend fun login(idToken: String): Result<LoginDto> =
            runCatching {
                val result = authService.login(LoginRequest(identityToken = idToken))
                result.getOrThrow().toData()
            }

        suspend fun postReissue(refreshToken: String): Result<TokenDto> =
            runCatching {
                authService.postRefresh(RefreshRequest(refreshToken)).getOrThrow().toData()
            }

        suspend fun validateToken(): Result<Unit> =
            runCatching {
                authService.validateToken().getOrThrow()
            }
    }
