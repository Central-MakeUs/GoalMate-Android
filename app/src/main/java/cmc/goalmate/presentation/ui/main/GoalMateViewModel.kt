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

        private var isFirstLoad: Boolean = true

        init {
            observeLoginStatus()
            observeGoalMateEvent()
        }

        private fun observeGoalMateEvent() {
            viewModelScope.launch {
                EventBus.subscribeEvent<GoalMateEvent> { event ->
                    when (event) {
                        GoalMateEvent.ReadComment -> {
                            decrementCommentBadgeCount()
                        }

                        is GoalMateEvent.RemainingTodayGoalCount -> {
                            updateRemainingTodoStatus(event.count)
                        }

                        GoalMateEvent.StartNewGoal -> {
                            fetchRemainingTodos()
                        }

                        else -> {}
                    }
                }
            }
        }

        override fun onLoginStateChanged(isLoggedIn: Boolean) {
            if (isLoggedIn) {
                fetchGoalMateState()
            } else {
                isFirstLoad = true
                _state.update { GoalMateUiState.INITIAL_STATE }
            }
        }

        private fun fetchGoalMateState() {
            fetchRemainingTodos()
            fetchNewComments()
        }

        private fun decrementCommentBadgeCount() {
            _state.update { current ->
                val updatedBadgeCounts = current.badgeCounts.toMutableMap().apply {
                    val updatedCount = (this[BottomNavItem.COMMENTS] ?: 0) - 1
                    this[BottomNavItem.COMMENTS] = maxOf(0, updatedCount)
                }
                current.copy(badgeCounts = updatedBadgeCounts)
            }
        }

        private fun updateRemainingTodoStatus(updatedCount: Int) {
            if (updatedCount == 0) {
                _state.update { current ->
                    current.copy(hasRemainingTodos = false)
                }
                return
            }
            if (!state.value.hasRemainingTodos) {
                _state.update { current ->
                    current.copy(hasRemainingTodos = true)
                }
            }
        }

        private fun fetchRemainingTodos() {
            viewModelScope.launch {
                val hasRemainingTodos = handleDomainResult(menteeGoalRepository.hasRemainingTodosToday())

                _state.update { current ->
                    current.copy(hasRemainingTodos = hasRemainingTodos)
                }
            }
        }

        private fun fetchNewComments() {
            viewModelScope.launch {
                val updatedCommentCount = handleDomainResult(commentRepository.getNewCommentCount())

                _state.update { current ->
                    val updatedBadgeCounts = current.badgeCounts.toMutableMap().apply {
                        this[BottomNavItem.COMMENTS] = updatedCommentCount
                    }
                    current.copy(badgeCounts = updatedBadgeCounts)
                }
            }
        }

        fun updateComments() {
            if (!isLoggedIn.value || isFirstLoad) {
                isFirstLoad = false
                return
            }

            viewModelScope.launch {
                val updatedCommentCount = handleDomainResult(commentRepository.getNewCommentCount())
                if (updatedCommentCount == state.value.currentCommentBadgeCount) return@launch

                _state.update { current ->
                    val updatedBadgeCounts = current.badgeCounts.toMutableMap().apply {
                        this[BottomNavItem.COMMENTS] = updatedCommentCount
                    }
                    current.copy(badgeCounts = updatedBadgeCounts)
                }
            }
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
