package cmc.goalmate.remote.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserInfoResponse(
    val id: Int,
    val name: String?,
    @SerialName("in_progress_goal_count") val inProgressGoalCount: Int,
    @SerialName("completed_goal_count") val completedGoalCount: Int,
    @SerialName("free_participation_count") val freeParticipationCount: Int,
    @SerialName("mentee_status") val menteeStatus: String,
)
