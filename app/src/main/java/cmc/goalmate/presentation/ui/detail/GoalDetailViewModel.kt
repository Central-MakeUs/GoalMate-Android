package cmc.goalmate.presentation.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import cmc.goalmate.app.navigation.Screen
import cmc.goalmate.domain.onFailure
import cmc.goalmate.domain.onSuccess
import cmc.goalmate.domain.repository.AuthRepository
import cmc.goalmate.domain.repository.GoalsRepository
import cmc.goalmate.presentation.ui.auth.asUiText
import cmc.goalmate.presentation.ui.common.LoginStateViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface GoalDetailUiState {
    data object Loading : GoalDetailUiState

    data class Success(val isLoggedIn: Boolean, val goal: GoalDetailUiModel) : GoalDetailUiState

    data class Error(val error: String) : GoalDetailUiState
}

fun GoalDetailUiState.getGoalOrNull(): GoalDetailUiModel? = (this as? GoalDetailUiState.Success)?.goal

@HiltViewModel
class GoalDetailViewModel
    @Inject
    constructor(
        savedStateHandle: SavedStateHandle,
        authRepository: AuthRepository,
        private val goalsRepository: GoalsRepository,
    ) : LoginStateViewModel(authRepository) {
        private val goalId = savedStateHandle.toRoute<Screen.GoalDetail.Detail>().goalId

        private val _state = MutableStateFlow<GoalDetailUiState>(GoalDetailUiState.Loading)
        val state: StateFlow<GoalDetailUiState> = _state
            .onStart {
                loadGoalDetail(goalId)
            }.stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000L),
                GoalDetailUiState.Loading,
            )

        private fun loadGoalDetail(id: Int) {
            viewModelScope.launch {
                goalsRepository.getGoalDetail(goalId = id)
                    .onSuccess { goalDetail ->
                        _state.value =
                            GoalDetailUiState.Success(isLoggedIn = isLoggedIn.value, goal = goalDetail.toUi())
                    }
                    .onFailure {
                        _state.value = GoalDetailUiState.Error(it.asUiText())
                    }
            }
        }
    }
