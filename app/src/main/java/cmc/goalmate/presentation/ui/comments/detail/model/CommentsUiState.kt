package cmc.goalmate.presentation.ui.comments.detail.model

import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDate

sealed interface CommentsUiState {
    data object Loading : CommentsUiState

    data class Success(
        val comments: List<CommentUiModel>,
        val commentTextState: CommentTextState,
    ) : CommentsUiState {
        private val lastMessageSentDate: LocalDate?
            get() = comments.lastOrNull { it.messages.any { it.sender == SenderUiModel.MENTEE } }?.date

        val canSendMessage: Boolean
            get() = (lastMessageSentDate != LocalDate.now()) || commentTextState != CommentTextState.Empty

        val canSubmit: Boolean
            get() = commentTextState == CommentTextState.Filled
    }

    data object Error : CommentsUiState
}

fun StateFlow<CommentsUiState>.successData(): CommentsUiState.Success = (this.value as CommentsUiState.Success)

fun CommentsUiState.success(): CommentsUiState.Success = (this as CommentsUiState.Success)

sealed interface CommentTextState {
    data object UnChanged : CommentTextState

    data object Empty : CommentTextState

    data object Filled : CommentTextState
}
