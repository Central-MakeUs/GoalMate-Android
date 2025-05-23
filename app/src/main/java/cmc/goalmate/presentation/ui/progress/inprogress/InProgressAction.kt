package cmc.goalmate.presentation.ui.progress.inprogress

import cmc.goalmate.presentation.ui.progress.inprogress.model.DailyProgressUiModel

sealed interface InProgressAction {
    data class CheckTodo(
        val todoId: Int,
        val currentState: Boolean,
    ) : InProgressAction

    data class SelectDate(
        val selectedDate: DailyProgressUiModel,
    ) : InProgressAction

    data class ViewPreviousWeek(
        val currentPageWeekIndex: Int,
    ) : InProgressAction

    data object NavigateToGoalDetail : InProgressAction

    data object NavigateToComment : InProgressAction

    data object Retry : InProgressAction
}

sealed interface InProgressEvent {
    data object TodoModificationNotAllowed : InProgressEvent

    data class NavigateToComment(
        val commentRoomId: Int,
        val endDate: String,
    ) : InProgressEvent

    data class NavigateToGoalDetail(
        val goalId: Int,
    ) : InProgressEvent
}
