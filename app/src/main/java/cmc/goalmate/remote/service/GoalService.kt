package cmc.goalmate.remote.service

import cmc.goalmate.remote.dto.base.ApiResponse
import cmc.goalmate.remote.dto.response.BaseResponse
import cmc.goalmate.remote.dto.response.GoalCreationResponse
import cmc.goalmate.remote.dto.response.GoalDetailResponse
import cmc.goalmate.remote.dto.response.GoalsResponse
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface GoalService {
    @GET(BASE_URL)
    suspend fun getGoals(): ApiResponse<BaseResponse<GoalsResponse>>

    @GET("$BASE_URL/{goalId}")
    suspend fun getGoalDetail(
        @Path("goalId") goalId: Int,
    ): ApiResponse<BaseResponse<GoalDetailResponse>>

    @POST("$BASE_URL/{goalId}/mentees")
    suspend fun createGoalForMentee(
        @Path("goalId") goalId: Int,
    ): ApiResponse<BaseResponse<GoalCreationResponse>>

    companion object {
        private const val BASE_URL = "/goals"
    }
}
