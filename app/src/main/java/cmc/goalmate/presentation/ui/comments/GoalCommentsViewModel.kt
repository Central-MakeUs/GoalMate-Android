package cmc.goalmate.presentation.ui.comments

import cmc.goalmate.domain.repository.UserRepository
import cmc.goalmate.presentation.ui.comments.GoalCommentsUiState.Companion.initialCommentsUiState
import cmc.goalmate.presentation.ui.comments.model.GoalCommentUiModel
import cmc.goalmate.presentation.ui.common.LoginStateViewModel
import cmc.goalmate.presentation.ui.common.UserState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

data class GoalCommentsUiState(
    val isLoading: Boolean,
    val userGoalsState: UserState<List<GoalCommentUiModel>>,
) {
    companion object {
        fun initialCommentsUiState(): GoalCommentsUiState =
            GoalCommentsUiState(
                isLoading = true,
                userGoalsState = UserState.LoggedOut,
            )
    }
}

@HiltViewModel
class GoalCommentsViewModel
    @Inject
    constructor(userRepository: UserRepository) : LoginStateViewModel(userRepository) {
        private val _state = MutableStateFlow(initialCommentsUiState())
        val state: StateFlow<GoalCommentsUiState>
            get() = _state

        init {
            if (isLoggedIn.value) {
                // 코멘트 목록 가져와서 업데이트
            } else {
                _state.value =
                    GoalCommentsUiState(isLoading = false, userGoalsState = UserState.LoggedOut)
            }
        }
    }
