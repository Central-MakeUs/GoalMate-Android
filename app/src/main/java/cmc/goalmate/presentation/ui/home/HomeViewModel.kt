package cmc.goalmate.presentation.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cmc.goalmate.domain.onFailure
import cmc.goalmate.domain.onSuccess
import cmc.goalmate.domain.repository.GoalsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface HomeUiState {
    data object Loading : HomeUiState

    data class Success(val goals: List<GoalUiModel>) : HomeUiState
}

@HiltViewModel
class HomeViewModel
    @Inject
    constructor(private val goalsRepository: GoalsRepository) : ViewModel() {
        private val _state: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState.Loading)
        val state: StateFlow<HomeUiState>
            get() = _state

        init {
            viewModelScope.launch {
                goalsRepository.getGoals()
                    .onSuccess { result ->
                        _state.value = HomeUiState.Success(result.goals.map { it.toUi() })
                    }
                    .onFailure { }
            }
        }
    }
