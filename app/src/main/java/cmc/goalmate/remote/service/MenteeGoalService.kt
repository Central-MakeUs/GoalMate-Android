package cmc.goalmate.remote.service

import cmc.goalmate.remote.dto.base.ApiResponse
import cmc.goalmate.remote.dto.request.TodoRequest
import cmc.goalmate.remote.dto.response.BaseResponse
import cmc.goalmate.remote.dto.response.DailyTodoResponse
import cmc.goalmate.remote.dto.response.MenteeGoalsResponse
import cmc.goalmate.remote.dto.response.TodoResponse
import cmc.goalmate.remote.dto.response.WeeklyProgressResponse
import cmc.goalmate.remote.service.MenteeGoalService.Companion.BASE_URL
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
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

    @PATCH("$BASE_URL/goals/{menteeGoalId}/todos/{todoId}")
    suspend fun updateTodo(
        @Path("menteeGoalId") menteeGoalId: Int,
        @Path("todoId") todoId: Int,
        @Body request: TodoRequest,
    ): ApiResponse<BaseResponse<TodoResponse>>

    companion object {
        private const val BASE_URL = "/mentees/my"
    }
}
