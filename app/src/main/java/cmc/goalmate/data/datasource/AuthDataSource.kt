package cmc.goalmate.data.datasource

import cmc.goalmate.data.model.LoginDto
import cmc.goalmate.data.model.toData
import cmc.goalmate.data.util.getOrThrow
import cmc.goalmate.remote.dto.request.LoginRequest
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
    }
