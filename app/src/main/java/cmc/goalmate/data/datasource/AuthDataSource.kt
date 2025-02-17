package cmc.goalmate.data.datasource

import android.util.Log
import cmc.goalmate.data.exception.HttpException
import cmc.goalmate.data.exception.NetworkException
import cmc.goalmate.data.exception.UnknownException
import cmc.goalmate.data.model.LoginDto
import cmc.goalmate.data.model.toData
import cmc.goalmate.remote.dto.base.ApiResponse
import cmc.goalmate.remote.dto.request.LoginRequest
import cmc.goalmate.remote.service.AuthService
import javax.inject.Inject

class AuthDataSource
    @Inject
    constructor(private val authService: AuthService) {
        suspend fun login(idToken: String): Result<LoginDto> =
            runCatching {
                Log.d("yenny", "request : ${LoginRequest(idToken)}")
                val result = authService.login(LoginRequest(identityToken = idToken))
                Log.d("yenny", "result : $result")
                result.getOrThrow().data.toData()
            }
    }

fun <T> ApiResponse<T>.getOrThrow(): T =
    when (this) {
        is ApiResponse.Success -> data
        is ApiResponse.Failure -> {
            throw when (this) {
                is ApiResponse.Failure.HttpException ->
                    HttpException(code = this.code, throwable = this.throwable)
                is ApiResponse.Failure.NetworkException ->
                    NetworkException(throwable = this.throwable)
                is ApiResponse.Failure.UnknownError ->
                    UnknownException(throwable = this.throwable)
            }
        }
    }
