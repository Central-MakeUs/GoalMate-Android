package cmc.goalmate.remote.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GoalsResponse(
    val goals: List<GoalResponse>,
    val page: PageResponse,
)

@Serializable
data class GoalResponse(
    val id: Int,
    val title: String,
    val topic: String,
    val description: String,
    val period: Int,
    @SerialName("daily_duration") val dailyDuration: Int,
    @SerialName("participants_limit") val participantsLimit: Int,
    @SerialName("current_participants") val currentParticipants: Int,
    @SerialName("is_closing_soon") val isClosingSoon: Boolean,
    @SerialName("goal_status") val goalStatus: String,
    @SerialName("mentor_name") val mentorName: String,
    @SerialName("created_at") val createdAt: String,
    @SerialName("updated_at") val updatedAt: String,
    @SerialName("main_image") val mainImage: String?,
)
