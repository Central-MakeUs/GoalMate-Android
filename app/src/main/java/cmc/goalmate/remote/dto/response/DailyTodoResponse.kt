package cmc.goalmate.remote.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DailyTodoResponse(
    @SerialName("mentee_goal") val menteeGoal: MenteeGoalResponse,
    val todos: List<TodoResponse>,
)

@Serializable
data class TodoResponse(
    val id: Int,
    @SerialName("todo_date") val todoDate: String,
    @SerialName("estimated_minutes") val estimatedMinutes: Int,
    val description: String,
    @SerialName("mentor_tip") val mentorTip: String?,
    @SerialName("todo_status") val todoStatus: String,
)
