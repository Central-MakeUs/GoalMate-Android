package cmc.goalmate.remote.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeeklyProgressResponse(
    val progress: List<ProgressResponse>,
    @SerialName("has_last_week") val hasLastWeek: Boolean,
    @SerialName("has_next_week") val hasNextWeek: Boolean,
)

@Serializable
data class ProgressResponse(
    val date: String,
    @SerialName("day_of_week") val dayOfWeek: String,
    @SerialName("daily_todo_count") val dailyTodoCount: Int,
    @SerialName("completed_daily_todo_count") val completedDailyTodoCount: Int,
    @SerialName("is_valid") val isValid: Boolean,
)
