package cmc.goalmate.presentation.ui.progress.inprogress

import cmc.goalmate.presentation.ui.progress.inprogress.model.DailyProgressUiModel
import java.time.YearMonth

sealed interface InProgressAction {
    data class CheckTodo(val todoId: Int, val updatedChecked: Boolean) : InProgressAction

    data class UpdateNextMonth(val currentYearMonth: YearMonth) : InProgressAction

    data class UpdatePreviousMonth(val currentYearMonth: YearMonth) : InProgressAction

    data class SelectDate(val selectedDate: DailyProgressUiModel) : InProgressAction
}
