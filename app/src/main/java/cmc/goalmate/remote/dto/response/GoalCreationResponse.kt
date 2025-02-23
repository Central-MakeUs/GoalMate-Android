package cmc.goalmate.remote.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GoalCreationResponse(
    @SerialName("mentee_goal_id") val menteeGoalId: Int,
    @SerialName("comment_room_id") val commentRoomId: Int,
)
