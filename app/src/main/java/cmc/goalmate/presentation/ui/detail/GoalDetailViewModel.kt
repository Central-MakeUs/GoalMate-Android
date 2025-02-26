package cmc.goalmate.presentation.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import cmc.goalmate.app.navigation.Screen
import cmc.goalmate.domain.onFailure
import cmc.goalmate.domain.onSuccess
import cmc.goalmate.domain.repository.AuthRepository
import cmc.goalmate.domain.repository.GoalsRepository
import cmc.goalmate.presentation.ui.common.LoginStateViewModel
import cmc.goalmate.presentation.ui.detail.navigation.GoalSummary
import cmc.goalmate.presentation.ui.util.asUiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface GoalDetailUiState {
    data object Loading : GoalDetailUiState

    data class Success(val isLoggedIn: Boolean, val goal: GoalDetailUiModel) : GoalDetailUiState

    data class Error(val error: String) : GoalDetailUiState
}

fun GoalDetailUiState.goalSummary(): GoalSummary = (this as GoalDetailUiState.Success).goal.toSummary()

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

        private val _event = Channel<GoalDetailEvent>()
        val event = _event.receiveAsFlow()

        private fun loadGoalDetail(id: Int) {
            viewModelScope.launch {
                goalsRepository.getGoalDetail(goalId = id)
                    .onSuccess { goalDetail ->
                        _state.value =
                            GoalDetailUiState.Success(
                                isLoggedIn = isLoggedIn.value,
                                goal = goalDetail.toUi(),
                            )
                    }
                    .onFailure {
                        _state.value = GoalDetailUiState.Error(it.asUiText())
                    }
            }
        }

        fun onAction(goalDetailAction: GoalDetailAction) {
            when (goalDetailAction) {
                GoalDetailAction.InitiateGoal -> {
                    viewModelScope.launch {
                        if (isLoggedIn.value) {
                            _event.send(GoalDetailEvent.ShowGoalStartConfirmation)
                        } else {
                            _event.send(GoalDetailEvent.NavigateToLogin)
                        }
                    }
                }

                GoalDetailAction.ConfirmGoalStart -> {
                    startGoal()
                }
            }
        }

        private fun startGoal() {
            viewModelScope.launch {
                goalsRepository.startGoal(goalId)
                    .onSuccess { startedGoal ->
                        _event.send(
                            GoalDetailEvent.NavigateToGoalStart(
                                newGoalId = startedGoal.newGoalId,
                                goalSummary = state.value.goalSummary(),
                                newCommentRoomId = startedGoal.newCommentRoomId,
                            ),
                        )
                    }
                    .onFailure { }
            }
        }
    }
