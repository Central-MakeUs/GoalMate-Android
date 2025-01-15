package cmc.goalmate.presentation.ui.home

import androidx.lifecycle.ViewModel
import cmc.goalmate.presentation.ui.home.HomeUiState.Companion.initialHomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

data class HomeUiState(
    val goals: List<GoalUiModel>,
) {
    companion object {
        fun initialHomeUiState(): HomeUiState =
            HomeUiState(
                goals = dummyGoals,
            )
    }
}

@HiltViewModel
class HomeViewModel
    @Inject
    constructor() : ViewModel() {
        private val _state = MutableStateFlow(initialHomeUiState())
        val state: StateFlow<HomeUiState>
            get() = _state
    }
