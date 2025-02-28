package cmc.goalmate.presentation.ui.comments.model

import cmc.goalmate.domain.model.CommentRoom
import cmc.goalmate.domain.model.CommentRooms
import cmc.goalmate.presentation.ui.mygoals.MyGoalUiState
import cmc.goalmate.presentation.ui.util.calculateDaysBetween
import java.time.LocalDate

data class CommentRoomsUiModel(
    val roomId: Int,
    val imageUrl: String,
    val mentorName: String,
    val title: String,
    val startDate: String,
    val endDate: String,
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
            endDate = "",
            title = "ANNA와 함께하는 영어 완전 정복 30일 목표입니다 블라블라블라",
            remainingDays = 23,
            goalState = MyGoalUiState.IN_PROGRESS,
            hasNewComment = true,
        )
    }
}

fun CommentRooms.toUi(): List<CommentRoomsUiModel> = this.rooms.map { it.toUi() }

fun CommentRoom.toUi(): CommentRoomsUiModel =
    CommentRoomsUiModel(
        roomId = commentRoomId,
        imageUrl = mentorProfileImage ?: "",
        mentorName = mentorName,
        title = menteeGoalTitle,
        startDate = startDate.toString(),
        endDate = endDate.toString(),
        remainingDays = calculateDaysBetween(endDate),
        goalState = if (endDate >= LocalDate.now()) MyGoalUiState.IN_PROGRESS else MyGoalUiState.COMPLETED,
        hasNewComment = newCommentsCount > 0,
    )
