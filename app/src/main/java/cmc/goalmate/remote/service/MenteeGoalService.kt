package cmc.goalmate.remote.service

import cmc.goalmate.remote.dto.base.ApiResponse
import cmc.goalmate.remote.dto.response.BaseResponse
import cmc.goalmate.remote.dto.response.MenteeGoalsResponse
import retrofit2.http.GET

interface MenteeGoalService {
    @GET("$BASE_URL/goals")
    suspend fun getMenteeGoals(): ApiResponse<BaseResponse<MenteeGoalsResponse>>

    companion object {
        private const val BASE_URL = "/mentees/my"
    }
}
