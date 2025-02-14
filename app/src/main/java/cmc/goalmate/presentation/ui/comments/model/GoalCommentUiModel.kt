package cmc.goalmate.presentation.ui.comments.model

import cmc.goalmate.presentation.ui.mygoals.MyGoalState

data class GoalCommentUiModel(
    val goalId: Long,
    val imageUrl: String,
    val mentorName: String,
    val title: String,
    val remainingDays: Int,
    val goalState: MyGoalState,
    val hasNewComment: Boolean = false,
) {
    companion object {
        val DUMMY = GoalCommentUiModel(
            goalId = 0L,
            imageUrl = "",
            mentorName = "ANNA",
            title = "ANNA와 함께하는 영어 완전 정복 30일 목표입니다 블라블라블라",
            remainingDays = 23,
            goalState = MyGoalState.IN_PROGRESS,
            hasNewComment = true,
        )

        val DUMMY2 = GoalCommentUiModel(
            goalId = 1L,
            imageUrl = "",
            mentorName = "ANNA",
            title = "ANNA와 함께하는 영어 완전 정복 30일 목표",
            remainingDays = 0,
            goalState = MyGoalState.IN_PROGRESS,
            hasNewComment = true,
        )
    }
}
