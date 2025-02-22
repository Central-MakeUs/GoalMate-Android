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
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
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
        private val route = savedStateHandle.toRoute<Screen.CompletedGoal>()
        private val goalId = route.goalId
        private val commentRoomId = route.roomId

        private val _event = Channel<CompletedGoalEvent>()
        val event = _event.receiveAsFlow()

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

        fun onAction(action: CompletedGoalAction) {
            when (action) {
                CompletedGoalAction.NavigateToCommentDetail -> sendEvent(
                    CompletedGoalEvent.NavigateToCommentDetail(
                        roomId = commentRoomId,
                        goalTitle = (state.value as CompletedGoalUiState.Success).goal.title,
                        startDate = (state.value as CompletedGoalUiState.Success).goal.startDate,
                    ),
                )

                CompletedGoalAction.NavigateToGoalDetail -> sendEvent(
                    CompletedGoalEvent.NavigateToGoalDetail(
                        goalId = goalId,
                    ),
                )
            }
        }

        private fun sendEvent(event: CompletedGoalEvent) {
            viewModelScope.launch {
                _event.send(event)
            }
        }
    }

sealed interface CompletedGoalAction {
    data object NavigateToGoalDetail : CompletedGoalAction

    data object NavigateToCommentDetail : CompletedGoalAction
}

sealed interface CompletedGoalEvent {
    data class NavigateToGoalDetail(val goalId: Int) : CompletedGoalEvent

    data class NavigateToCommentDetail(val roomId: Int, val goalTitle: String, val startDate: String) : CompletedGoalEvent
}
