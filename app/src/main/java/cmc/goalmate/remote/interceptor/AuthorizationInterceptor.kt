package cmc.goalmate.remote.interceptor

import cmc.goalmate.local.TokenDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

class AuthorizationInterceptor
    @Inject
    constructor(private val tokenDataStore: TokenDataStore) : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response =
            runBlocking {
                val accessToken = tokenDataStore.accessToken.first()
                val request = if (accessToken.isNotEmpty()) {
                    chain.request()
                        .newBuilder()
                        .addHeader(AUTHORIZATION, "Bearer $accessToken")
                        .build()
                } else {
                    chain.request()
                }
                chain.proceed(request)
            }

        companion object {
            private const val AUTHORIZATION = "authorization"

            fun addBearerTokenToRequest(
                request: Request,
                newAccessToken: String,
            ): Request =
                request.newBuilder()
                    .removeHeader(AUTHORIZATION)
                    .addHeader(AUTHORIZATION, newAccessToken)
                    .build()
        }
    }
