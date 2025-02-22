package cmc.goalmate.remote.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MenteeGoalsResponse(
    @SerialName("mentee_goals") val menteeGoals: List<MenteeGoalResponse>,
    val page: PageResponse,
)

@Serializable
data class MenteeGoalResponse(
    val id: Int,
    val title: String,
    val topic: String,
    @SerialName("mentor_name") val mentorName: String,
    @SerialName("main_image") val mainImage: String?,
    @SerialName("start_date") val startDate: String,
    @SerialName("end_date") val endDate: String,
    @SerialName("mentor_letter") val finalComment: String?,
    @SerialName("today_todo_count") val todayTodoCount: Int,
    @SerialName("today_completed_count") val todayCompletedCount: Int,
    @SerialName("today_remaining_count") val todayRemainingCount: Int,
    @SerialName("total_todo_count") val totalTodoCount: Int,
    @SerialName("total_completed_count") val totalCompletedCount: Int,
    @SerialName("comment_room_id") val commentRoomId: Int,
    @SerialName("mentee_goal_status") val menteeGoalStatus: String,
    @SerialName("created_at") val createdAt: String,
    @SerialName("updated_at") val updatedAt: String,
)
