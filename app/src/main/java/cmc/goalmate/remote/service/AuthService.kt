package cmc.goalmate.remote.service

import cmc.goalmate.remote.dto.base.ApiResponse
import cmc.goalmate.remote.dto.request.LoginRequest
import cmc.goalmate.remote.dto.request.RefreshRequest
import cmc.goalmate.remote.dto.response.BaseResponse
import cmc.goalmate.remote.dto.response.LoginResponse
import cmc.goalmate.remote.dto.response.TokenResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface AuthService {
    @POST("$BASE_PATH/login")
    suspend fun login(
        @Body request: LoginRequest,
    ): ApiResponse<BaseResponse<LoginResponse>>

    @PUT("$BASE_PATH/reissue")
    suspend fun postRefresh(
        @Body request: RefreshRequest,
    ): ApiResponse<BaseResponse<TokenResponse>>

    @GET("$BASE_PATH/validate")
    suspend fun validateToken(): ApiResponse<BaseResponse<Unit>>

    companion object {
        private const val BASE_PATH = "/auth"
    }
}
