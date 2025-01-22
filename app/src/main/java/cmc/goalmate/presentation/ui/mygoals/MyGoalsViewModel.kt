package cmc.goalmate.presentation.ui.mygoals

import androidx.lifecycle.ViewModel
import cmc.goalmate.presentation.ui.mygoals.MyGoalsUiState.Companion.initialMyGoalsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

data class MyGoalsUiState(
    val myGoals: List<MyGoalUiModel>,
) {
    fun hasNoGoals(): Boolean = myGoals.isEmpty()

    companion object {
        fun initialMyGoalsUiState(): MyGoalsUiState = MyGoalsUiState(myGoals = listOf(MyGoalUiModel.DUMMY, MyGoalUiModel.DUMMY2))
    }
}

@HiltViewModel
class
MyGoalsViewModel
    @Inject
    constructor() : ViewModel() {
        private val _state = MutableStateFlow(initialMyGoalsUiState())
        val state: StateFlow<MyGoalsUiState>
            get() = _state
    }
