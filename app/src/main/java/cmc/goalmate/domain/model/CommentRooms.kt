package cmc.goalmate.domain.model

import java.time.LocalDate

data class CommentRooms(val rooms: List<CommentRoom>)

data class CommentRoom(
    val commentRoomId: Int,
    val menteeGoalId: Int,
    val menteeGoalTitle: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val mentorName: String,
    val newCommentsCount: Int,
)
