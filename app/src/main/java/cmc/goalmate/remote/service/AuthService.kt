package cmc.goalmate.remote.service

import cmc.goalmate.remote.dto.base.ApiResponse
import cmc.goalmate.remote.dto.request.LoginRequest
import cmc.goalmate.remote.dto.response.BaseResponse
import cmc.goalmate.remote.dto.response.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("$BASE_PATH/login")
    suspend fun login(
        @Body request: LoginRequest,
    ): ApiResponse<BaseResponse<LoginResponse>>

    companion object {
        private const val BASE_PATH = "/auth"
    }
}
