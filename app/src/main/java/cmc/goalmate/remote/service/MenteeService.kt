package cmc.goalmate.remote.service

import cmc.goalmate.remote.dto.base.ApiResponse
import cmc.goalmate.remote.dto.response.BaseResponse
import cmc.goalmate.remote.dto.response.NickNameValidationResponse
import cmc.goalmate.remote.dto.response.UserInfoResponse
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Query

interface MenteeService {
    @PUT("${BASE_PATH}/my/name")
    suspend fun setNickName(
        @Query("name") nickName: String,
    ): ApiResponse<BaseResponse<String>>

    @GET("${BASE_PATH}/name/validate")
    suspend fun isAvailableNickName(
        @Query("name") nickName: String,
    ): ApiResponse<BaseResponse<NickNameValidationResponse>>

    @GET("${BASE_PATH}/my")
    suspend fun getUserInfo(): ApiResponse<BaseResponse<UserInfoResponse>>

    companion object {
        private const val BASE_PATH = "/mentees"
    }
}
