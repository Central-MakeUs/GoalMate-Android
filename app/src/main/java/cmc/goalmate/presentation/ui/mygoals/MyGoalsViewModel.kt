package cmc.goalmate.presentation.ui.mygoals

import androidx.lifecycle.viewModelScope
import cmc.goalmate.domain.DomainResult
import cmc.goalmate.domain.repository.AuthRepository
import cmc.goalmate.domain.repository.MenteeGoalRepository
import cmc.goalmate.presentation.ui.common.LoginStateViewModel
import cmc.goalmate.presentation.ui.util.asUiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

sealed interface MyGoalsUiState {
    data object Loading : MyGoalsUiState

    data class LoggedIn(val myGoals: List<MyGoalUiModel>) : MyGoalsUiState

    data object LoggedOut : MyGoalsUiState

    data class Error(val error: String) : MyGoalsUiState
}

@HiltViewModel
class MyGoalsViewModel
    @Inject
    constructor(
        authRepository: AuthRepository,
        private val menteeGoalRepository: MenteeGoalRepository,
    ) : LoginStateViewModel(authRepository) {
        val state: StateFlow<MyGoalsUiState> = isLoggedIn
            .map { isLoggedIn ->
                if (isLoggedIn) {
                    when (val result = menteeGoalRepository.getMenteeGoals()) {
                        is DomainResult.Success -> MyGoalsUiState.LoggedIn(result.data.toUi())
                        is DomainResult.Error -> MyGoalsUiState.Error(result.error.asUiText())
                    }
                } else {
                    MyGoalsUiState.LoggedOut
                }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = MyGoalsUiState.Loading,
            )
    }
