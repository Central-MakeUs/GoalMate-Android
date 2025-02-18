package cmc.goalmate.remote.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CommentRoomsResponse(
    @SerialName("comment_rooms") val commentRooms: List<CommentRoomResponse>,
    val page: PageResponse,
)

@Serializable
data class CommentRoomResponse(
    @SerialName("comment_room_id") val commentRoomId: Int,
    @SerialName("mentee_goal_id") val menteeGoalId: Int,
    @SerialName("mentee_goal_title") val menteeGoalTitle: String,
    @SerialName("start_date") val startDate: String,
    @SerialName("end_date") val endDate: String,
    @SerialName("mentor_name") val mentorName: String,
    @SerialName("new_comments_count") val newCommentsCount: Int,
)
