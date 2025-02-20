package cmc.goalmate.domain.model

import java.time.LocalDate
import java.time.YearMonth

data class WeeklyProgress(
    val yearMonth: YearMonth,
    val progressData: List<DailyProgress>,
    val hasLastWeek: Boolean,
    val hasNextWeek: Boolean,
)

sealed class DailyProgress {
    data class ValidProgress(
        val date: LocalDate,
        val dayOfWeek: Weekday,
        val dailyTodoCount: Int,
        val completedDailyTodoCount: Int,
    ) : DailyProgress() {
        fun getStatus(compared: LocalDate = LocalDate.now()): ProgressStatus =
            when {
                date < compared -> ProgressStatus.Completed
                date == compared -> ProgressStatus.InProgress
                else -> ProgressStatus.NotStarted
            }
    }

    data class InvalidProgress(
        val date: LocalDate,
        val dayOfWeek: Weekday,
    ) : DailyProgress()
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
