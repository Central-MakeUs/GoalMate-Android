package cmc.goalmate.presentation.ui.progress.completed

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cmc.goalmate.app.navigation.CommentDetailParams
import cmc.goalmate.app.navigation.NavigateToCommentDetail
import cmc.goalmate.app.navigation.NavigateToGoal
import cmc.goalmate.presentation.components.AppBarWithBackButton
import cmc.goalmate.presentation.ui.util.ObserveAsEvent

@Composable
fun CompletedScreen(
    navigateToComments: NavigateToCommentDetail,
    navigateToGoalDetail: NavigateToGoal,
    navigateToHome: () -> Unit,
    navigateBack: () -> Unit,
    viewModel: CompletedGoalViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvent(viewModel.event) { event ->
        when (event) {
            is CompletedGoalEvent.NavigateToCommentDetail -> {
                navigateToComments(
                    CommentDetailParams(
                        roomId = event.roomId,
                        goalTitle = event.goalTitle,
                        startDate = event.startDate,
                    ),
                )
            }
            is CompletedGoalEvent.NavigateToGoalDetail -> {
                navigateToGoalDetail(event.goalId)
            }
        }
    }

    Column {
        AppBarWithBackButton(
            onBackButtonClicked = navigateBack,
            title = "목표 완료",
        )
        CompletedScreenContent(
            state = state,
            navigateToComments = navigateToComments,
            navigateToGoalDetail = navigateToGoalDetail,
            navigateToHome = navigateToHome,
        )
    }
}

@Composable
private fun CompletedScreenContent(
    state: CompletedGoalUiState,
    navigateToComments: NavigateToCommentDetail,
    navigateToGoalDetail: NavigateToGoal,
    navigateToHome: () -> Unit,
) {
    when (state) {
        CompletedGoalUiState.Error -> {}
        CompletedGoalUiState.Loading -> {}
        is CompletedGoalUiState.Success -> {
            MyGoalCompletedContent(
                completedGoal = state.goal,
                navigateToHome = { navigateToHome() },
                navigateToGoalDetail = { navigateToGoalDetail(state.goal.id) },
                navigateToCommentDetail = {
                    // ROOM ID랑 Title 넘기기
                },
            )
        }
    }
}
