package cmc.goalmate.presentation.ui.mygoals

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cmc.goalmate.domain.DomainResult
import cmc.goalmate.domain.repository.AuthRepository
import cmc.goalmate.domain.repository.MenteeGoalRepository
import cmc.goalmate.presentation.ui.util.EventBus
import cmc.goalmate.presentation.ui.util.GoalMateEvent
import cmc.goalmate.presentation.ui.util.asUiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface MyGoalsUiState {
    data object Loading : MyGoalsUiState

    data class LoggedIn(val myGoals: List<MyGoalUiModel>) : MyGoalsUiState

    data object LoggedOut : MyGoalsUiState

    data class Error(val error: String) : MyGoalsUiState
}

fun MyGoalsUiState.currentMyGoals(): List<MyGoalUiModel> = (this as? MyGoalsUiState.LoggedIn)?.myGoals ?: emptyList()

fun List<MyGoalUiModel>.remainingGoalsCount(): Int = this.sumOf { it.remainGoals }

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class MyGoalsViewModel
    @Inject
    constructor(
        private val authRepository: AuthRepository,
        private val menteeGoalRepository: MenteeGoalRepository,
    ) : ViewModel() {
        private val reloadTrigger = MutableSharedFlow<Unit>(replay = 1, extraBufferCapacity = 1)
        private val _state = MutableStateFlow<MyGoalsUiState>(MyGoalsUiState.Loading)
        val state: StateFlow<MyGoalsUiState> = _state.asStateFlow()

        init {
            handleStateUpdates()
            observeGoalMateEvents()
            triggerReload()
        }

        private fun handleStateUpdates() {
            viewModelScope.launch {
                combine(
                    authRepository.isLogin(),
                    reloadTrigger.onCompletion { reloadTrigger.resetReplayCache() },
                ) { isLogin, _ ->
                    if (isLogin) {
                        when (val result = menteeGoalRepository.getMenteeGoals()) {
                            is DomainResult.Success -> {
                                val updatedMyGoals = result.data.toUi()
                                postEvent(updatedMyGoals)
                                MyGoalsUiState.LoggedIn(updatedMyGoals)
                            }
                            is DomainResult.Error -> MyGoalsUiState.Error(result.error.asUiText())
                        }
                    } else {
                        MyGoalsUiState.LoggedOut
                    }
                }.collect { newState ->
                    _state.value = newState
                }
            }
        }

        private fun observeGoalMateEvents() {
            viewModelScope.launch {
                EventBus.subscribeEvent<GoalMateEvent.TodoCheckChanged> { event ->
                    updateTodoCheckCount(menteeGoalId = event.menteeGoalId, updatedCount = event.remainingTodos)
                }
            }
            viewModelScope.launch {
                EventBus.subscribeEvent<GoalMateEvent.StartNewGoal> {
                    triggerReload()
                }
            }
        }

        private fun triggerReload() {
            viewModelScope.launch {
                reloadTrigger.emit(Unit)
            }
        }

        private fun postEvent(updatedMyGoals: List<MyGoalUiModel>) {
            viewModelScope.launch {
                EventBus.postEvent(event = GoalMateEvent.RemainingTodayGoalCount(updatedMyGoals.remainingGoalsCount()))
            }
        }

        private fun updateTodoCheckCount(
            menteeGoalId: Int,
            updatedCount: Int,
        ) {
            val currentMyGoals = state.value.currentMyGoals()
            val goalToUpdate = currentMyGoals.find { it.menteeGoalId == menteeGoalId }
            if (goalToUpdate == null) {
                triggerReload()
                return
            }
            val previousRemainGoals = goalToUpdate.remainGoals

            val newCompletedTodoCount = when {
                updatedCount < previousRemainGoals -> {
                    goalToUpdate.totalCompletedTodoCount + (previousRemainGoals - updatedCount)
                }
                updatedCount > previousRemainGoals -> {
                    goalToUpdate.totalCompletedTodoCount - (updatedCount - previousRemainGoals)
                }
                else -> {
                    goalToUpdate.totalCompletedTodoCount
                }
            }

            val newGoals = currentMyGoals.map { myGoal ->
                if (myGoal.menteeGoalId == menteeGoalId) {
                    myGoal.copy(
                        remainGoals = updatedCount,
                        totalCompletedTodoCount = newCompletedTodoCount,
                    )
                } else {
                    myGoal
                }
            }
            postEvent(updatedMyGoals = newGoals)
            _state.update { MyGoalsUiState.LoggedIn(myGoals = newGoals) }
        }
    }
