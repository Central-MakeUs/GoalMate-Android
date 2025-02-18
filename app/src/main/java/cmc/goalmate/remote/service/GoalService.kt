package cmc.goalmate.remote.service

import cmc.goalmate.remote.dto.base.ApiResponse
import cmc.goalmate.remote.dto.response.BaseResponse
import cmc.goalmate.remote.dto.response.GoalDetailResponse
import cmc.goalmate.remote.dto.response.GoalsResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface GoalService {
    @GET(BASE_URL)
    suspend fun getGoals(): ApiResponse<BaseResponse<GoalsResponse>>

    @GET("$BASE_URL/{goalId}")
    suspend fun getGoalDetail(
        @Path("goalId") goalId: Int,
    ): ApiResponse<BaseResponse<GoalDetailResponse>>

    companion object {
        private const val BASE_URL = "/goals"
    }
}
