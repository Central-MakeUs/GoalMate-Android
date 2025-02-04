package cmc.goalmate.presentation.ui.comments

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cmc.goalmate.app.navigation.NavigateToGoal
import cmc.goalmate.presentation.components.HeaderTitle
import cmc.goalmate.presentation.ui.common.EmptyGoalContents
import cmc.goalmate.presentation.ui.mygoals.MyGoalState

data class GoalCommentsUiState(
    val myGoals: List<GoalCommentUiModel>,
) {
    fun hasNoComments(): Boolean = myGoals.isEmpty()

    companion object {
        fun initialCommentsUiState(): GoalCommentsUiState =
            GoalCommentsUiState(
                myGoals = listOf(
                    GoalCommentUiModel.DUMMY,
                    GoalCommentUiModel.DUMMY2,
                ),
            )
    }
}

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
            title = "ANNA와 함께하는 영어 완전 정복 30일 목표",
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
            hasNewComment = false,
        )
    }
}

@Composable
fun GoalCommentsScreen(
    navigateToCommentDetail: NavigateToGoal,
    navigateToHome: () -> Unit,
) {
    val state = GoalCommentsUiState.initialCommentsUiState()

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        HeaderTitle(
            title = "코멘트",
            modifier = Modifier.fillMaxWidth(),
        )

        if (state.hasNoComments()) {
            EmptyGoalContents(
                onButtonClicked = navigateToHome,
                modifier = Modifier.fillMaxSize(),
            )
        } else {
            GoalCommentsContent(
                state = state,
                navigateToCommentDetail = navigateToCommentDetail,
            )
        }
    }
}
