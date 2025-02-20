package cmc.goalmate.remote.service

import cmc.goalmate.remote.dto.base.ApiResponse
import cmc.goalmate.remote.dto.response.BaseResponse
import cmc.goalmate.remote.dto.response.DailyTodoResponse
import cmc.goalmate.remote.dto.response.MenteeGoalsResponse
import cmc.goalmate.remote.dto.response.WeeklyProgressResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MenteeGoalService {
    @GET("$BASE_URL/goals")
    suspend fun getMenteeGoals(): ApiResponse<BaseResponse<MenteeGoalsResponse>>

    @GET("$BASE_URL/goals/{menteeGoalId}/weekly-progress")
    suspend fun getWeeklyProgress(
        @Path("menteeGoalId") menteeGoalId: Int,
        @Query("date") date: String? = null,
    ): ApiResponse<BaseResponse<WeeklyProgressResponse>>

    @GET("$BASE_URL/goals/{menteeGoalId}")
    suspend fun getDailyTodo(
        @Path("menteeGoalId") menteeGoalId: Int,
        @Query("date") date: String? = null,
    ): ApiResponse<BaseResponse<DailyTodoResponse>>

    companion object {
        private const val BASE_URL = "/mentees/my"
    }
}
