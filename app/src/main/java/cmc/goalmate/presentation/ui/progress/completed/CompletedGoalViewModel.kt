package cmc.goalmate.presentation.ui.progress.completed

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import cmc.goalmate.app.navigation.Screen
import cmc.goalmate.domain.onFailure
import cmc.goalmate.domain.onSuccess
import cmc.goalmate.domain.repository.MenteeGoalRepository
import cmc.goalmate.presentation.ui.progress.completed.model.CompletedGoalUiModel
import cmc.goalmate.presentation.ui.progress.completed.model.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface CompletedGoalUiState {
    data object Loading : CompletedGoalUiState

    data class Success(val goal: CompletedGoalUiModel) : CompletedGoalUiState

    data object Error : CompletedGoalUiState
}

@HiltViewModel
class CompletedGoalViewModel
    @Inject
    constructor(
        savedStateHandle: SavedStateHandle,
        private val menteeGoalRepository: MenteeGoalRepository,
    ) : ViewModel() {
        private val goalId = savedStateHandle.toRoute<Screen.CompletedGoal>().goalId

        private val _state = MutableStateFlow<CompletedGoalUiState>(CompletedGoalUiState.Loading)
        val state: StateFlow<CompletedGoalUiState> = _state
            .onStart {
                loadGoalInfo(goalId)
            }.stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000L),
                CompletedGoalUiState.Loading,
            )

        private fun loadGoalInfo(id: Int) {
            viewModelScope.launch {
                menteeGoalRepository.getGoalInfo(id)
                    .onSuccess { goal ->
                        _state.value = CompletedGoalUiState.Success(goal.toUi())
                    }
                    .onFailure {
                        _state.value = CompletedGoalUiState.Error
                    }
            }
        }
    }
