package cmc.goalmate.presentation.ui.progress.inprogress

import cmc.goalmate.presentation.ui.progress.inprogress.model.DailyProgressUiModel

sealed interface InProgressAction {
    data class CheckTodo(val todoId: Int, val updatedChecked: Boolean) : InProgressAction

    data object UpdateNextMonth : InProgressAction

    data object UpdatePreviousMonth : InProgressAction

    data class SelectDate(val selectedDate: DailyProgressUiModel) : InProgressAction

    data object ClickUneditableGoal : InProgressAction

    data class NavigateToGoalDetail(val goalId: Int) : InProgressAction

    data class NavigateToComment(val goalId: Int) : InProgressAction
}
