package cmc.goalmate.presentation.ui.progress.inprogress

import cmc.goalmate.presentation.ui.progress.inprogress.model.DailyProgressUiModel

sealed interface InProgressAction {
    data class CheckTodo(val todoId: Int, val currentState: Boolean) : InProgressAction

    data object UpdateNextMonth : InProgressAction

    data object UpdatePreviousMonth : InProgressAction

    data class SelectDate(val selectedDate: DailyProgressUiModel) : InProgressAction

    data object NavigateToGoalDetail : InProgressAction

    data object NavigateToComment : InProgressAction
}

sealed interface InProgressEvent {
    data object TodoModificationNotAllowed : InProgressEvent

    data class NavigateToComment(val commentRoomId: Int) : InProgressEvent

    data class NavigateToGoalDetail(val goalId: Int) : InProgressEvent
}
