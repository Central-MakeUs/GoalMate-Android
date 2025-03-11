package cmc.goalmate.presentation.ui.progress.completed

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import cmc.goalmate.domain.onFailure
import cmc.goalmate.domain.onSuccess
import cmc.goalmate.domain.repository.MenteeGoalRepository
import cmc.goalmate.domain.repository.UserRepository
import cmc.goalmate.presentation.ui.main.navigation.Screen
import cmc.goalmate.presentation.ui.progress.completed.model.CompletedGoalUiModel
import cmc.goalmate.presentation.ui.progress.completed.model.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface CompletedGoalUiState {
    data object Loading : CompletedGoalUiState

    data class Success(
        val goal: CompletedGoalUiModel,
    ) : CompletedGoalUiState

    data object Error : CompletedGoalUiState
}

@HiltViewModel
class CompletedGoalViewModel
    @Inject
    constructor(
        savedStateHandle: SavedStateHandle,
        private val menteeGoalRepository: MenteeGoalRepository,
        private val userRepository: UserRepository,
    ) : ViewModel() {
        private val route = savedStateHandle.toRoute<Screen.CompletedGoal>()
        private val menteeGoalId = route.menteeGoalId
        private val commentRoomId = route.roomId
        private val goalId = route.goalId

        private val _event = Channel<CompletedGoalEvent>()
        val event = _event.receiveAsFlow()

        private val _state = MutableStateFlow<CompletedGoalUiState>(CompletedGoalUiState.Loading)
        val state: StateFlow<CompletedGoalUiState> =
            _state
                .onStart { loadCompletedGoal() }
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5000L),
                    initialValue = CompletedGoalUiState.Loading,
                )

        private fun loadCompletedGoal() {
            viewModelScope.launch {
                menteeGoalRepository
                    .getGoalInfo(menteeGoalId)
                    .onSuccess { goal ->
                        val menteeName = userRepository.getNickName().first()
                        _state.update { CompletedGoalUiState.Success(goal.toUi(menteeName = menteeName)) }
                    }.onFailure {
                        _state.update { CompletedGoalUiState.Error }
                    }
            }
        }

        fun onAction(action: CompletedGoalAction) {
            when (action) {
                CompletedGoalAction.NavigateToCommentDetail ->
                    sendEvent(
                        CompletedGoalEvent.NavigateToCommentDetail(
                            roomId = commentRoomId,
                            goalTitle = (state.value as CompletedGoalUiState.Success).goal.title,
                            endDate = (state.value as CompletedGoalUiState.Success).goal.endDate.toString(),
                        ),
                    )

                CompletedGoalAction.NavigateToGoalDetail ->
                    sendEvent(
                        CompletedGoalEvent.NavigateToGoalDetail(
                            goalId = goalId,
                        ),
                    )

                CompletedGoalAction.Retry -> {
                    loadCompletedGoal()
                }
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

    data object Retry : CompletedGoalAction
}

sealed interface CompletedGoalEvent {
    data class NavigateToGoalDetail(
        val goalId: Int,
    ) : CompletedGoalEvent

    data class NavigateToCommentDetail(
        val roomId: Int,
        val goalTitle: String,
        val endDate: String,
    ) : CompletedGoalEvent
}
