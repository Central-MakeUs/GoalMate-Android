package cmc.goalmate.presentation.ui.progress.inprogress

import cmc.goalmate.presentation.ui.progress.inprogress.model.CalendarUiModel
import cmc.goalmate.presentation.ui.progress.inprogress.model.DailyProgressUiModel
import cmc.goalmate.presentation.ui.progress.inprogress.model.ProgressStatus
import cmc.goalmate.presentation.ui.progress.inprogress.model.TodoGoalUiModel
import java.time.LocalDate
import java.time.YearMonth

data class InProgressUiState(
    val weeklyData: CalendarUiModel,
    val selectedDate: DailyProgressUiModel,
    val todos: List<TodoGoalUiModel>,
    val goalInfo: GoalOverViewUiModel,
) {
    val currentAchievementRate: Int
        get() = when (val status = selectedDate.status) {
            is ProgressStatus.InProgress -> calculateInProgressAchievement().toInt()
            is ProgressStatus.Completed -> status.actualProgress
            else -> 0
        }

    private fun calculateInProgressAchievement(): Float {
        val totalTodos = todos.size
        val completedTodos = todos.count { it.isCompleted }

        return if (totalTodos > 0) {
            (completedTodos / totalTodos.toFloat()) * 100f
        } else {
            0f
        }
    }

    fun canModifyTodoCheck(
        yearMonth: YearMonth = YearMonth.now(),
        date: Int = LocalDate.now().dayOfMonth,
    ): Boolean = weeklyData.yearMonth == yearMonth && selectedDate.date == date

    companion object {
        fun initialInProgressUiState(): InProgressUiState =
            InProgressUiState(
                weeklyData = CalendarUiModel.DUMMY,
                selectedDate = DailyProgressUiModel(date = 24, status = ProgressStatus.InProgress),
                todos = TodoGoalUiModel.DUMMY,
                goalInfo = GoalOverViewUiModel.DUMMY,
            )
    }
}

data class GoalOverViewUiModel(val goalId: Long, val title: String, val mentor: String) {
    companion object {
        val DUMMY = GoalOverViewUiModel(0L, "다온과 함께하는 영어 완전 정복 목표 입니당", "다온")
    }
}
