package cmc.goalmate.presentation.ui.comments.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import cmc.goalmate.app.navigation.Screen
import cmc.goalmate.domain.model.Writer
import cmc.goalmate.domain.onFailure
import cmc.goalmate.domain.onSuccess
import cmc.goalmate.domain.repository.CommentRepository
import cmc.goalmate.presentation.ui.comments.detail.model.CommentUiModel
import cmc.goalmate.presentation.ui.comments.detail.model.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

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

@HiltViewModel
class CommentsViewModel
    @Inject
    constructor(
        savedStateHandle: SavedStateHandle,
        private val commentRepository: CommentRepository,
    ) : ViewModel() {
        private val roomId = savedStateHandle.toRoute<Screen.CommentsDetail>().roomId

        private val _state = MutableStateFlow<CommentsUiState>(CommentsUiState.Loading)
        val state: StateFlow<CommentsUiState> = _state
            .onStart {
                loadComments(roomId)
            }.stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000L),
                CommentsUiState.Loading,
            )

        private fun loadComments(id: Int) {
            viewModelScope.launch {
                commentRepository.getComments(id)
                    .onSuccess { comments ->
                        val commentsUiModel = comments.toUi()
                        val lastMenteeComment: LocalDate? = comments.comments.lastOrNull {
                            it.writerRole == Writer.MENTEE
                        }?.commentedAt?.toLocalDate()
                        _state.value = CommentsUiState.Success(commentsUiModel, lastMenteeComment)
                    }
                    .onFailure {
                        _state.value = CommentsUiState.Error
                    }
            }
        }

        fun onAction(action: CommentAction) {
            when (action) {
                CommentAction.CancelEdit -> {}
                is CommentAction.DeleteComment -> {}
                is CommentAction.EditComment -> {}
                is CommentAction.SendComment -> {}
            }
        }
    }

sealed interface CommentAction {
    data class SendComment(val content: String) : CommentAction

    data class EditComment(val commentId: Int) : CommentAction

    data object CancelEdit : CommentAction

    data class DeleteComment(val commentId: Int) : CommentAction
}
