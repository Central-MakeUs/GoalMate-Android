package cmc.goalmate.presentation.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cmc.goalmate.domain.DataError
import cmc.goalmate.domain.onFailure
import cmc.goalmate.domain.onSuccess
import cmc.goalmate.domain.repository.GoalsRepository
import cmc.goalmate.presentation.ui.util.EventBus
import cmc.goalmate.presentation.ui.util.GoalMateEvent
import cmc.goalmate.presentation.ui.util.asUiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface HomeUiState {
    data object Loading : HomeUiState

    data class Success(
        val goals: List<GoalUiModel>,
    ) : HomeUiState

    data class Error(
        val message: String,
    ) : HomeUiState
}

@HiltViewModel
class HomeViewModel
    @Inject
    constructor(
        private val goalsRepository: GoalsRepository,
    ) : ViewModel() {
        private val _state: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState.Loading)
        val state: StateFlow<HomeUiState> =
            _state
                .onStart { loadGoals() }
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5000L),
                    initialValue = HomeUiState.Loading,
                )

        private fun loadGoals() {
            viewModelScope.launch {
                goalsRepository
                    .getGoals()
                    .onSuccess { result ->
                        _state.value = HomeUiState.Success(result.goals.map { it.toUi() })
                    }.onFailure {
                        if (it == DataError.Network.NO_INTERNET) {
                            EventBus.postEvent(GoalMateEvent.NoInternet)
                        }
                        _state.value = HomeUiState.Error(it.asUiText())
                    }
            }
        }

        fun onAction(action: HomeAction) {
            when (action) {
                HomeAction.Retry -> {
                    viewModelScope.launch {
                        _state.emit(HomeUiState.Loading)
                        loadGoals()
                    }
                }
            }
        }
    }

sealed interface HomeAction {
    data object Retry : HomeAction
}
