package cmc.goalmate.presentation.ui.progress.completed

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cmc.goalmate.presentation.components.AppBarWithBackButton
import cmc.goalmate.presentation.theme.GoalMateDimens
import cmc.goalmate.presentation.theme.GoalMateTheme
import cmc.goalmate.presentation.ui.main.navigation.CommentDetailParams
import cmc.goalmate.presentation.ui.main.navigation.NavigateToCommentDetail
import cmc.goalmate.presentation.ui.main.navigation.NavigateToGoal
import cmc.goalmate.presentation.ui.progress.completed.model.CompletedGoalUiModel
import cmc.goalmate.presentation.ui.progress.components.ProgressBottomButton
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
                        endDate = event.endDate,
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
            onAction = viewModel::onAction,
            navigateToHome = navigateToHome,
            modifier = Modifier.fillMaxSize(),
        )
    }
}

@Composable
private fun CompletedScreenContent(
    state: CompletedGoalUiState,
    onAction: (CompletedGoalAction) -> Unit,
    navigateToHome: () -> Unit,
    modifier: Modifier = Modifier,
) {
    when (state) {
        CompletedGoalUiState.Error -> {}
        CompletedGoalUiState.Loading -> {}
        is CompletedGoalUiState.Success -> {
            Box(
                modifier = modifier,
            ) {
                MyGoalCompletedContent(
                    completedGoal = state.goal,
                    navigateToGoalDetail = { onAction(CompletedGoalAction.NavigateToGoalDetail) },
                    navigateToCommentDetail = {
                        onAction(CompletedGoalAction.NavigateToCommentDetail)
                    },
                    modifier = Modifier
                        .padding(horizontal = GoalMateDimens.HorizontalPadding)
                        .padding(top = GoalMateDimens.BottomMargin),
                )
                ProgressBottomButton(
                    buttonText = "다음 목표 시작하기",
                    onClicked = { navigateToHome() },
                    modifier = Modifier.align(Alignment.BottomCenter),
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun CompletedScreenContentPreview() {
    GoalMateTheme {
        CompletedScreenContent(
            state = CompletedGoalUiState.Success(CompletedGoalUiModel.DUMMY),
            onAction = {},
            navigateToHome = {},
        )
    }
}
