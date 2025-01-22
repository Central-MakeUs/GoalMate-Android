package cmc.goalmate.presentation.ui.progress.inprogress

import cmc.goalmate.presentation.ui.progress.inprogress.model.CalendarUiModel
import cmc.goalmate.presentation.ui.progress.inprogress.model.TodoGoalUiModel
import java.time.LocalDate
import java.time.YearMonth

data class InProgressUiState(
    val weeklyData: CalendarUiModel,
    val selectedDate: Int,
    val todos: List<TodoGoalUiModel>,
    val goalInfo: GoalOverViewUiModel,
) {
    fun canModifyTodoCheck(
        yearMonth: YearMonth = YearMonth.now(),
        date: Int = LocalDate.now().dayOfMonth,
    ): Boolean = weeklyData.yearMonth == yearMonth && selectedDate == date

    companion object {
        fun initialInProgressUiState(): InProgressUiState =
            InProgressUiState(
                weeklyData = CalendarUiModel.DUMMY,
                selectedDate = 24,
                todos = TodoGoalUiModel.DUMMY,
                goalInfo = GoalOverViewUiModel.DUMMY,
            )
    }
}

data class GoalOverViewUiModel(val goalId: Long, val title: String, val mentor: String) {
    companion object {
        val DUMMY = GoalOverViewUiModel(0L, "다온과 함께하는 영어 완전 정복", "다온")
    }
}
