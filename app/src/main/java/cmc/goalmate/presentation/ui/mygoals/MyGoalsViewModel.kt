package cmc.goalmate.presentation.ui.mygoals

import cmc.goalmate.domain.repository.AuthRepository
import cmc.goalmate.presentation.ui.common.LoginStateViewModel
import cmc.goalmate.presentation.ui.common.UserState
import cmc.goalmate.presentation.ui.mygoals.MyGoalsUiState.Companion.initialMyGoalsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

data class MyGoalsUiState(
    val isLoading: Boolean,
    val userGoalsState: UserState<List<MyGoalUiModel>>,
) {
    companion object {
        fun initialMyGoalsUiState(): MyGoalsUiState = MyGoalsUiState(isLoading = true, userGoalsState = UserState.LoggedOut)
    }
}

@HiltViewModel
class MyGoalsViewModel
    @Inject
    constructor(authRepository: AuthRepository) : LoginStateViewModel(authRepository) {
        private val _state = MutableStateFlow(initialMyGoalsUiState())
        val state: StateFlow<MyGoalsUiState>
            get() = _state

        init {
            if (isLoggedIn.value) {
                // 목표 목록 가져와서 업데이트
            } else {
                _state.value = MyGoalsUiState(isLoading = false, userGoalsState = UserState.LoggedOut)
            }
        }

        private fun loadMyGoals() {
            // 레포지토리에서 목표 목록 가져오기
        }
    }
