package cmc.goalmate.presentation.ui.comments

import androidx.lifecycle.viewModelScope
import cmc.goalmate.domain.DomainResult
import cmc.goalmate.domain.repository.AuthRepository
import cmc.goalmate.domain.repository.CommentRepository
import cmc.goalmate.presentation.ui.comments.model.CommentRoomsUiModel
import cmc.goalmate.presentation.ui.comments.model.toUi
import cmc.goalmate.presentation.ui.common.LoginStateViewModel
import cmc.goalmate.presentation.ui.util.EventBus
import cmc.goalmate.presentation.ui.util.GoalMateEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface GoalCommentsUiState {
    data object Loading : GoalCommentsUiState

    data class LoggedIn(val commentRooms: List<CommentRoomsUiModel>) : GoalCommentsUiState

    data object LoggedOut : GoalCommentsUiState

    data object Error : GoalCommentsUiState
}

fun GoalCommentsUiState.hasNoGoals(): Boolean = (this as GoalCommentsUiState.LoggedIn).commentRooms.isEmpty()

@HiltViewModel
class GoalCommentsViewModel
    @Inject
    constructor(
        authRepository: AuthRepository,
        private val commentRepository: CommentRepository,
    ) : LoginStateViewModel(authRepository) {
        private val _state: MutableStateFlow<GoalCommentsUiState> = MutableStateFlow(GoalCommentsUiState.Loading)
        val state: StateFlow<GoalCommentsUiState> = _state.asStateFlow()

        private val _event = Channel<CommentRoomsEvent>()
        val event = _event.receiveAsFlow()

        override fun onLoginStateChanged(isLoggedIn: Boolean) {
            if (isLoggedIn) {
                viewModelScope.launch {
                    _state.value = when (val result = commentRepository.getCommentRooms()) {
                        is DomainResult.Success -> GoalCommentsUiState.LoggedIn(result.data.toUi())
                        is DomainResult.Error -> GoalCommentsUiState.Error
                    }
                }
            } else {
                _state.value = GoalCommentsUiState.LoggedOut
            }
        }

        fun onAction(action: CommentRoomsAction) {
            when (action) {
                is CommentRoomsAction.SelectCommentRoom -> {
                    selectCommentRoom(action.roomId)
                }
            }
        }

        private fun sendEvent(event: CommentRoomsEvent) {
            viewModelScope.launch {
                _event.send(event)
            }
        }

        private fun selectCommentRoom(roomId: Int) {
            val commentRoom =
                (state.value as GoalCommentsUiState.LoggedIn)
                    .commentRooms
                    .firstOrNull { it.roomId == roomId } ?: return

            if (commentRoom.hasNewComment) {
                viewModelScope.launch {
                    EventBus.postEvent(GoalMateEvent.ReadComment)
                }

                _state.update { current ->
                    (current as GoalCommentsUiState.LoggedIn).copy(
                        commentRooms = current.commentRooms.map { room ->
                            if (room.roomId == roomId) {
                                room.copy(hasNewComment = false)
                            } else {
                                room
                            }
                        },
                    )
                }
            }

            sendEvent(
                CommentRoomsEvent.NavigateToCommentDetail(
                    roomId = roomId,
                    goalTitle = commentRoom.title,
                    endDate = commentRoom.endDate,
                ),
            )
        }
    }

sealed interface CommentRoomsAction {
    data class SelectCommentRoom(val roomId: Int) : CommentRoomsAction
}

sealed interface CommentRoomsEvent {
    data class NavigateToCommentDetail(
        val roomId: Int,
        val goalTitle: String,
        val endDate: String,
    ) : CommentRoomsEvent
}
