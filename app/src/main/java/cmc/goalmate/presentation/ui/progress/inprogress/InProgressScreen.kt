package cmc.goalmate.presentation.ui.progress.inprogress

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cmc.goalmate.R
import cmc.goalmate.app.navigation.CommentDetailParams
import cmc.goalmate.app.navigation.NavigateToCommentDetail
import cmc.goalmate.app.navigation.NavigateToGoal
import cmc.goalmate.presentation.components.AppBarWithBackButton
import cmc.goalmate.presentation.components.GoalMateDialog
import cmc.goalmate.presentation.theme.goalMateColors
import cmc.goalmate.presentation.theme.goalMateTypography
import cmc.goalmate.presentation.ui.progress.components.ProgressBottomButton
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
                navigateToComments(CommentDetailParams(event.commentRoomId, goalTitle, event.startDate))
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

        Box(modifier = Modifier.weight(1f)) {
            InProgressScreenContent(
                state = state,
                onAction = viewModel::onAction,
                modifier = Modifier.fillMaxSize(),
            )
            ProgressBottomButton(
                buttonText = "멘토 코멘트 받으러 가기",
                onClicked = { viewModel.onAction(InProgressAction.NavigateToComment) },
                modifier = Modifier.align(Alignment.BottomCenter),
            )
        }
    }

    if (isDialogVisible) {
        GoalMateDialog(
            buttonText = "오늘 목표 완료 하기",
            onConfirmation = {
                isDialogVisible = false
            },
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
