package cmc.goalmate.presentation.ui.progress.inprogress

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cmc.goalmate.R
import cmc.goalmate.app.navigation.NavigateToCommentDetail
import cmc.goalmate.app.navigation.NavigateToGoal
import cmc.goalmate.presentation.components.AppBarWithBackButton
import cmc.goalmate.presentation.components.GoalMateDialog
import cmc.goalmate.presentation.ui.util.ObserveAsEvent

@Composable
fun InProgressScreen(
    goalTitle: String,
    navigateToComments: NavigateToCommentDetail,
    navigateToGoalDetail: NavigateToGoal,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InProgressViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    var isDialogVisible by rememberSaveable { mutableStateOf(false) }

    ObserveAsEvent(viewModel.event) { event ->
        when (event) {
            is InProgressEvent.NavigateToComment -> {
                navigateToComments(event.commentRoomId, goalTitle)
            }
            is InProgressEvent.NavigateToGoalDetail -> {
                navigateToGoalDetail(event.goalId)
            }
            InProgressEvent.TodoModificationNotAllowed -> {
                isDialogVisible = true
            }
        }
    }

    Column(
        modifier = modifier,
    ) {
        AppBarWithBackButton(
            onBackButtonClicked = navigateBack,
            title = goalTitle,
        )

        InProgressScreenContent(
            state = state,
            onAction = viewModel::onAction,
            modifier = Modifier.fillMaxSize(),
        )
    }

    if (isDialogVisible) {
        GoalMateDialog(
            contentText = stringResource(R.string.goal_in_progress_uneditable_warning_message),
            buttonText = "오늘 목표 완료 하기",
            onConfirmation = {
                isDialogVisible = false
            },
        )
    }
}
