package cmc.goalmate.remote.service

import cmc.goalmate.remote.dto.base.ApiResponse
import cmc.goalmate.remote.dto.response.BaseResponse
import cmc.goalmate.remote.dto.response.GoalsResponse
import retrofit2.http.GET

interface GoalService {
    @GET(BASE_URL)
    suspend fun getGoals(): ApiResponse<BaseResponse<GoalsResponse>>

    companion object {
        private const val BASE_URL = "/goals"
    }
}
