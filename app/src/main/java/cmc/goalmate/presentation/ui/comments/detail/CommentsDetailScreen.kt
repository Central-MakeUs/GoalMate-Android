package cmc.goalmate.presentation.ui.comments.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cmc.goalmate.presentation.components.AppBarWithBackButton
import cmc.goalmate.presentation.components.ErrorScreen
import cmc.goalmate.presentation.components.GoalMateIconDialog
import cmc.goalmate.presentation.theme.GoalMateDimens
import cmc.goalmate.presentation.ui.comments.components.CommentTextField
import cmc.goalmate.presentation.ui.comments.detail.model.CommentsUiState
import cmc.goalmate.presentation.ui.util.ObserveAsEvent

@Composable
fun CommentsDetailScreen(
    goalTitle: String,
    navigateBack: () -> Unit,
    viewModel: CommentsDetailViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    var showCancelButton by rememberSaveable { mutableStateOf(false) }
    var isDialogVisible by rememberSaveable { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }

    ObserveAsEvent(viewModel.event) { event ->
        when (event) {
            is CommentEvent.StartEditComment -> {
                showCancelButton = true
                focusRequester.requestFocus()
            }

            CommentEvent.ShowSendingError -> {
                isDialogVisible = true
            }

            CommentEvent.CancelEdit -> {
                showCancelButton = false
                focusManager.clearFocus()
            }

            CommentEvent.SuccessSending -> {
                showCancelButton = false
                focusManager.clearFocus()
            }
        }
    }

    Column {
        AppBarWithBackButton(
            onBackButtonClicked = navigateBack,
            title = goalTitle,
        )
        Column(
            modifier = Modifier.padding(
                horizontal = GoalMateDimens.HorizontalPadding,
                vertical = GoalMateDimens.BottomMargin,
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            CommentsDetailContent(
                state = state,
                onAction = viewModel::onAction,
                modifier = Modifier.weight(1f),
            )
            CommentTextField(
                commentText = viewModel.commentContent,
                onAction = viewModel::onAction,
                enabled = (state as? CommentsUiState.Success)?.canSendMessage ?: true,
                isButtonEnabled = (state as? CommentsUiState.Success)?.canSubmit ?: false,
                showCancelButton = showCancelButton,
                modifier = Modifier.fillMaxWidth().padding(vertical = 11.dp),
                focusRequester = focusRequester,
            )
        }
    }

    if (isDialogVisible) {
        GoalMateIconDialog(
            subTitle = "하루 한 번!",
            contentText = "하루 1회만 코멘트를 입력할 수 있어요.\n" + "오늘 보낸 코멘트를 수정해주세요",
            buttonText = "확인했어요",
            onConfirmation = { isDialogVisible = false },
        )
    }
}

@Composable
private fun CommentsDetailContent(
    state: CommentsUiState,
    onAction: (CommentAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        when (state) {
            is CommentsUiState.Success -> {
                CommentsContent(
                    comments = state.comments,
                    onAction = onAction,
                    modifier = Modifier.fillMaxSize(),
                )
            }

            CommentsUiState.Error -> {
                ErrorScreen(modifier = Modifier.fillMaxSize())
            }
            CommentsUiState.Loading -> {}
        }
    }
}
