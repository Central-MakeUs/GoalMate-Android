package cmc.goalmate.domain.model

import java.time.LocalDate

data class GoalMateCalendar(
    val weeklyData: List<Week>,
    val weekNumber: Int,
)

data class Week(
    val dailyProgresses: List<DailyProgress>,
    val shouldLoadPrevious: Boolean,
)

sealed class DailyProgress(open val date: LocalDate) {
    data class ValidProgress(
        override val date: LocalDate,
        val dayOfWeek: Weekday,
        val dailyTodoCount: Int = 0,
        val completedDailyTodoCount: Int = 0,
    ) : DailyProgress(date) {
        fun getStatus(compared: LocalDate = LocalDate.now()): ProgressStatus =
            when {
                date < compared -> ProgressStatus.Completed
                date == compared -> ProgressStatus.InProgress
                else -> ProgressStatus.NotStarted
            }
    }

    data class InvalidProgress(
        override val date: LocalDate,
        val dayOfWeek: Weekday,
    ) : DailyProgress(date)
}

enum class Weekday {
    SUNDAY,
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY,
}

enum class ProgressStatus {
    Completed,
    InProgress,
    NotStarted,
}
