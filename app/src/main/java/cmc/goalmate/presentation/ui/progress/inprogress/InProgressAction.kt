package cmc.goalmate.presentation.ui.progress.inprogress

import cmc.goalmate.presentation.ui.progress.inprogress.model.DailyProgressUiModel
import java.time.YearMonth

sealed interface InProgressAction {
    data class CheckTodo(val todoId: Int, val updatedChecked: Boolean) : InProgressAction

    data class UpdateNextMonth(val currentYearMonth: YearMonth) : InProgressAction

    data class UpdatePreviousMonth(val currentYearMonth: YearMonth) : InProgressAction

    data class SelectDate(val selectedDate: DailyProgressUiModel) : InProgressAction

    data object ClickUneditableGoal : InProgressAction

    data class NavigateToComments(val goalId: Long) : InProgressAction

    data class NavigateToGoalDetail(val goalId: Long) : InProgressAction
}
