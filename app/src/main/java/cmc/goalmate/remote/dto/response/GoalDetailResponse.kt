package cmc.goalmate.remote.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GoalDetailResponse(
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
    @SerialName("weekly_objectives") val weeklyObjectives: List<WeeklyObjectiveResponse>,
    @SerialName("mid_objectives") val midObjectives: List<MidObjectiveResponse>,
    @SerialName("thumbnail_images") val thumbnailImages: List<ImageResponse>,
    @SerialName("content_images") val contentImages: List<ImageResponse>,
)

@Serializable
data class WeeklyObjectiveResponse(
    @SerialName("week_number") val weekNumber: Int,
    val description: String,
)

@Serializable
data class MidObjectiveResponse(
    val sequence: Int,
    val description: String,
)

@Serializable
data class ImageResponse(
    val sequence: Int,
    @SerialName("imageUrl") val imageUrl: String,
)
