package cmc.goalmate.presentation.ui.progress.inprogress

import android.app.Activity
import android.view.WindowManager
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cmc.goalmate.R
import cmc.goalmate.presentation.components.AppBarWithBackButton
import cmc.goalmate.presentation.components.GoalMateDialog
import cmc.goalmate.presentation.components.GoalMateIconDialog
import cmc.goalmate.presentation.theme.goalMateColors
import cmc.goalmate.presentation.theme.goalMateTypography
import cmc.goalmate.presentation.ui.main.navigation.CommentDetailParams
import cmc.goalmate.presentation.ui.main.navigation.NavigateToCommentDetail
import cmc.goalmate.presentation.ui.main.navigation.NavigateToGoal
import cmc.goalmate.presentation.ui.util.ComposableLifeCycle
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
    var isTodoModificationDialogVisible by rememberSaveable { mutableStateOf(false) }
    HandleScreenCapture()

    ObserveAsEvent(viewModel.event) { event ->
        when (event) {
            is InProgressEvent.NavigateToComment -> {
                navigateToComments(CommentDetailParams(roomId = event.commentRoomId, goalTitle = goalTitle, endDate = event.endDate))
            }
            is InProgressEvent.NavigateToGoalDetail -> {
                navigateToGoalDetail(event.goalId)
            }
            InProgressEvent.TodoModificationNotAllowed -> {
                isTodoModificationDialogVisible = true
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
            modifier = Modifier
                .weight(1f)
                .background(MaterialTheme.goalMateColors.background),
        )
    }

    TodoModificationDialog(
        isDialogVisible = isTodoModificationDialogVisible,
        onConfirmButtonClicked = { isTodoModificationDialogVisible = false },
    )
}

@Composable
private fun TodoModificationDialog(
    isDialogVisible: Boolean,
    onConfirmButtonClicked: () -> Unit,
) {
    if (isDialogVisible) {
        GoalMateDialog(
            buttonText = "오늘 목표 완료 하기",
            onConfirmation = onConfirmButtonClicked,
        ) {
            Text(
                text = stringResource(R.string.goal_in_progress_uneditable_warning_message),
                style = MaterialTheme.goalMateTypography.buttonLabelLarge,
                color = MaterialTheme.goalMateColors.onSurfaceVariant,
                textAlign = TextAlign.Center,
                modifier = it,
            )
        }
    }
}

@Composable
private fun ScreenShotDialog(
    isDialogVisible: Boolean,
    onConfirmButtonClicked: () -> Unit,
) {
    if (isDialogVisible) {
        GoalMateIconDialog(
            subTitle = stringResource(R.string.goal_in_progress_screenshot_warning_title),
            contentText = stringResource(R.string.goal_in_progress_screenshot_warning_message),
            buttonText = stringResource(R.string.goal_in_progress_screenshot_warning_button),
            onConfirmation = onConfirmButtonClicked,
        )
    }
}

@Composable
private fun HandleScreenCapture() {
    val activity = LocalContext.current as? Activity

    ComposableLifeCycle { _, event ->
        when (event) {
            Lifecycle.Event.ON_START -> {
                activity?.window?.setFlags(
                    WindowManager.LayoutParams.FLAG_SECURE,
                    WindowManager.LayoutParams.FLAG_SECURE,
                )
            }
            Lifecycle.Event.ON_PAUSE -> {
                activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_SECURE)
            }
            else -> {}
        }
    }
}
