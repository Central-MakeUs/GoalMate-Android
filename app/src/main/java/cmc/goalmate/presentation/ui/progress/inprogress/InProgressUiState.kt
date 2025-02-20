package cmc.goalmate.presentation.ui.progress.inprogress

import cmc.goalmate.presentation.ui.progress.inprogress.model.CalendarUiModel
import cmc.goalmate.presentation.ui.progress.inprogress.model.DailyProgressDetailUiModel
import cmc.goalmate.presentation.ui.progress.inprogress.model.GoalOverViewUiModel
import cmc.goalmate.presentation.ui.progress.inprogress.model.UiState
import java.time.LocalDate

data class InProgressUiState(
    val weeklyProgressState: UiState<CalendarUiModel>,
    val selectedDailyState: UiState<DailyProgressDetailUiModel>,
    val goalInfoState: UiState<GoalOverViewUiModel>,
    val selectedDate: Int = LocalDate.now().dayOfMonth,
) {
    companion object {
        val initialState = InProgressUiState(
            weeklyProgressState = UiState.Loading,
            selectedDailyState = UiState.Loading,
            goalInfoState = UiState.Loading,
        )
        val DUMMY = InProgressUiState(
            weeklyProgressState = UiState.Success(CalendarUiModel.DUMMY),
            selectedDailyState = UiState.Success(DailyProgressDetailUiModel.DUMMY),
            goalInfoState = UiState.Success(GoalOverViewUiModel.DUMMY),
        )
    }
}

fun InProgressUiState.updateTodoState(
    todoId: Int,
    isCompleted: Boolean,
): InProgressUiState {
    val updatedSelectedDailyState = when (val state = selectedDailyState) {
        is UiState.Success -> {
            val updatedTodos = state.data.todos.map { todo ->
                if (todo.id == todoId) {
                    todo.copy(isCompleted = isCompleted)
                } else {
                    todo
                }
            }
            UiState.Success(state.data.copy(todos = updatedTodos))
        }
        is UiState.Loading -> state
        is UiState.Error -> state
    }

    return this.copy(selectedDailyState = updatedSelectedDailyState)
}
