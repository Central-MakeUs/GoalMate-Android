package cmc.goalmate.presentation.ui.comments

import androidx.lifecycle.viewModelScope
import cmc.goalmate.domain.DomainResult
import cmc.goalmate.domain.repository.AuthRepository
import cmc.goalmate.domain.repository.CommentRepository
import cmc.goalmate.presentation.ui.comments.model.CommentRoomsUiModel
import cmc.goalmate.presentation.ui.comments.model.toUi
import cmc.goalmate.presentation.ui.common.LoginStateViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
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
        val state: StateFlow<GoalCommentsUiState> = isLoggedIn
            .map { isLoggedIn ->
                if (isLoggedIn) {
                    when (val result = commentRepository.getCommentRooms()) {
                        is DomainResult.Success -> GoalCommentsUiState.LoggedIn(result.data.toUi())
                        is DomainResult.Error -> GoalCommentsUiState.Error
                    }
                } else {
                    GoalCommentsUiState.LoggedOut
                }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = GoalCommentsUiState.Loading,
            )
    }
