package cmc.goalmate.presentation.ui.mygoals

import android.util.Log
import androidx.lifecycle.viewModelScope
import cmc.goalmate.domain.DomainResult
import cmc.goalmate.domain.repository.AuthRepository
import cmc.goalmate.domain.repository.MenteeGoalRepository
import cmc.goalmate.presentation.ui.common.LoginStateViewModel
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

    data object Error : MyGoalsUiState
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
                    Log.d("yenny", "load mentees goals")
                    val result = menteeGoalRepository.getMenteeGoals()
                    Log.d("yenny", "result -> $result")
                    when (result) {
                        is DomainResult.Success -> MyGoalsUiState.LoggedIn(result.data.toUi())
                        is DomainResult.Error -> MyGoalsUiState.Error
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
