package cmc.goalmate.presentation.ui.main

import androidx.lifecycle.viewModelScope
import cmc.goalmate.domain.DomainResult
import cmc.goalmate.domain.RootError
import cmc.goalmate.domain.repository.AuthRepository
import cmc.goalmate.domain.repository.CommentRepository
import cmc.goalmate.domain.repository.MenteeGoalRepository
import cmc.goalmate.presentation.ui.common.LoginStateViewModel
import cmc.goalmate.presentation.ui.main.GoalMateUiState.Companion.DEFAULT_NEW_COMMENT_COUNT
import cmc.goalmate.presentation.ui.main.GoalMateUiState.Companion.DEFAULT_REMAINING_VALUE
import cmc.goalmate.presentation.ui.util.EventBus
import cmc.goalmate.presentation.ui.util.GoalMateEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GoalMateViewModel
    @Inject
    constructor(
        private val menteeGoalRepository: MenteeGoalRepository,
        private val commentRepository: CommentRepository,
        private val authRepository: AuthRepository,
    ) : LoginStateViewModel(authRepository) {
        private val _state: MutableStateFlow<GoalMateUiState> = MutableStateFlow(GoalMateUiState.INITIAL_STATE)
        val state: StateFlow<GoalMateUiState> = _state.asStateFlow()

        init {
            viewModelScope.launch {
                EventBus.subscribeEvent<GoalMateEvent> { event ->
                    when (event) {
                        GoalMateEvent.ReadComment -> {
                            _state.update { current ->
                                val updatedBadgeCounts = current.badgeCounts.toMutableMap().apply {
                                    val updatedCount = (this[BottomNavItem.COMMENTS] ?: 0) - 1
                                    this[BottomNavItem.COMMENTS] = maxOf(0, updatedCount)
                                }
                                current.copy(badgeCounts = updatedBadgeCounts)
                            }
                        }

                        is GoalMateEvent.RemainingTodayGoalCount -> {
                            if (event.count == 0) {
                                _state.update { current ->
                                    current.copy(hasRemainingTodos = false)
                                }
                            } else {
                                if (!state.value.hasRemainingTodos) {
                                    _state.update { current ->
                                        current.copy(hasRemainingTodos = true)
                                    }
                                }
                            }
                        }

                        else -> {}
                    }
                }
            }
        }

        override fun onLoginStateChanged(isLoggedIn: Boolean) {
            if (!isLoggedIn) return

            viewModelScope.launch {
                val hasRemainingTodos = handleDomainResult(menteeGoalRepository.hasRemainingTodosToday())
                val newCommentCount = handleDomainResult(commentRepository.getNewCommentCount())
                val updatedState = GoalMateUiState(
                    hasRemainingTodos = hasRemainingTodos,
                    badgeCounts = mapOf(BottomNavItem.COMMENTS to newCommentCount),
                )
                _state.update { updatedState }
            }
        }
    }

data class GoalMateUiState(
    val hasRemainingTodos: Boolean,
    val badgeCounts: Map<BottomNavItem, Int>,
) {
    companion object {
        const val DEFAULT_REMAINING_VALUE = false
        const val DEFAULT_NEW_COMMENT_COUNT = 0
        val INITIAL_STATE = GoalMateUiState(
            hasRemainingTodos = DEFAULT_REMAINING_VALUE,
            badgeCounts = BottomNavItem.entries.associateWith { DEFAULT_NEW_COMMENT_COUNT },
        )
    }
}

private inline fun <reified T> handleDomainResult(result: DomainResult<T, RootError>): T =
    when (result) {
        is DomainResult.Success -> result.data
        is DomainResult.Error -> {
            when (T::class) {
                Boolean::class -> DEFAULT_REMAINING_VALUE
                Int::class -> DEFAULT_NEW_COMMENT_COUNT
                else -> throw IllegalArgumentException("Unknown type")
            } as T
        }
    }
