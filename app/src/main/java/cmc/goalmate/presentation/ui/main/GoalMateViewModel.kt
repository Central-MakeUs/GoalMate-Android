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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class GoalMateViewModel
    @Inject
    constructor(
        private val menteeGoalRepository: MenteeGoalRepository,
        private val commentRepository: CommentRepository,
        authRepository: AuthRepository,
    ) : LoginStateViewModel(authRepository) {
        val state: StateFlow<GoalMateUiState> = isLoggedIn
            .map { isLoggedIn ->
                if (isLoggedIn) {
                    val hasRemainingTodos = handleDomainResult(menteeGoalRepository.hasRemainingTodosToday())
                    val newCommentCount = handleDomainResult(commentRepository.getNewCommentCount())

                    GoalMateUiState(
                        hasRemainingTodos = hasRemainingTodos,
                        badgeCounts = mapOf(BottomNavItem.COMMENTS to newCommentCount),
                    )
                } else {
                    GoalMateUiState.INITIAL_STATE
                }
            }
            .stateIn(
                viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = GoalMateUiState.INITIAL_STATE,
            )
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
