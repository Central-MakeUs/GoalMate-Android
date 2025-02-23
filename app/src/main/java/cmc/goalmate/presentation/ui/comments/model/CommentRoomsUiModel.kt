package cmc.goalmate.presentation.ui.comments.model

import cmc.goalmate.domain.model.CommentRoom
import cmc.goalmate.domain.model.CommentRooms
import cmc.goalmate.presentation.ui.mygoals.MyGoalUiState
import java.time.LocalDate
import java.time.temporal.ChronoUnit

data class CommentRoomsUiModel(
    val roomId: Int,
    val imageUrl: String,
    val mentorName: String,
    val title: String,
    val startDate: String,
    val remainingDays: Int,
    val goalState: MyGoalUiState,
    val hasNewComment: Boolean = false,
) {
    companion object {
        val DUMMY = CommentRoomsUiModel(
            roomId = 0,
            imageUrl = "",
            mentorName = "ANNA",
            startDate = "",
            title = "ANNA와 함께하는 영어 완전 정복 30일 목표입니다 블라블라블라",
            remainingDays = 23,
            goalState = MyGoalUiState.IN_PROGRESS,
            hasNewComment = true,
        )
    }
}

fun CommentRooms.toUi(): List<CommentRoomsUiModel> = this.rooms.map { it.toUi() }

fun CommentRoom.toUi(): CommentRoomsUiModel {
    val daysFromStart = ChronoUnit.DAYS.between(LocalDate.now(), endDate).toInt().coerceAtLeast(0)
    return CommentRoomsUiModel(
        roomId = commentRoomId,
        imageUrl = mentorProfileImage ?: "",
        mentorName = mentorName,
        title = menteeGoalTitle,
        startDate = startDate.toString(),
        remainingDays = daysFromStart,
        goalState = if (endDate >= LocalDate.now()) MyGoalUiState.IN_PROGRESS else MyGoalUiState.COMPLETED,
        hasNewComment = newCommentsCount > 0,
    )
}
