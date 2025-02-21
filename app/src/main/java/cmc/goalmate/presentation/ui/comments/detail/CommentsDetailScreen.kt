package cmc.goalmate.presentation.ui.comments.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cmc.goalmate.presentation.components.AppBarWithBackButton
import cmc.goalmate.presentation.theme.GoalMateDimens
import cmc.goalmate.presentation.ui.comments.components.CommentTextField

@Composable
fun CommentsDetailScreen(
    goalTitle: String,
    navigateBack: () -> Unit,
    viewModel: CommentsViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    var commentText by rememberSaveable { mutableStateOf("") }
    var showCancelButton by rememberSaveable { mutableStateOf(false) }

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
            )
            CommentTextField(
                commentText = commentText,
                onCommentTextChanged = { commentText = it },
                onCancelButtonClicked = {
                    viewModel.onAction(CommentAction.CancelEdit)
                },
                onSubmitButtonClicked = {
                    viewModel.onAction(CommentAction.SendComment(commentText))
                },
                showCancelButton = showCancelButton,
                isCommentTextFieldEnabled = state is CommentsUiState.Success,
            )
        }
    }
}

@Composable
private fun CommentsDetailContent(
    state: CommentsUiState,
    onAction: (CommentAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    when (state) {
        is CommentsUiState.Success -> {
            CommentsContent(
                comments = state.comments,
                onAction = onAction,
                modifier = modifier,
            )
        }
        CommentsUiState.Error -> {}
        CommentsUiState.Loading -> {}
    }
}
