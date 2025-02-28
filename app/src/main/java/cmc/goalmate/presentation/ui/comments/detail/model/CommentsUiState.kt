package cmc.goalmate.presentation.ui.comments.detail.model

import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDate

sealed interface CommentsUiState {
    data object Loading : CommentsUiState

    data class Success(
        val comments: List<CommentUiModel>,
        val lastMessageSentDate: LocalDate?,
    ) : CommentsUiState {
        val canSendMessage: Boolean
            get() = lastMessageSentDate != LocalDate.now()
    }

    data object Error : CommentsUiState
}

fun StateFlow<CommentsUiState>.successData(): CommentsUiState.Success = (this.value as CommentsUiState.Success)

fun CommentsUiState.success(): CommentsUiState.Success = (this as CommentsUiState.Success)
