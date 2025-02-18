package cmc.goalmate.data.model

data class CommentRoomsDto(val rooms: List<CommentRoomDto>)

data class CommentRoomDto(
    val commentRoomId: Int,
    val menteeGoalId: Int,
    val menteeGoalTitle: String,
    val startDate: String,
    val endDate: String,
    val mentorName: String,
    val newCommentsCount: Int,
)
