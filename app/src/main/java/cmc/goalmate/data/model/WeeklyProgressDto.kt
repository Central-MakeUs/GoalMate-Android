package cmc.goalmate.data.model

import cmc.goalmate.domain.model.DailyProgress
import cmc.goalmate.domain.model.Weekday
import cmc.goalmate.domain.model.WeeklyProgress
import cmc.goalmate.remote.dto.response.ProgressResponse
import cmc.goalmate.remote.dto.response.WeeklyProgressResponse
import java.time.LocalDate
import java.time.YearMonth

data class WeeklyProgressDto(
    val progressList: List<ProgressDto>,
    val hasLastWeek: Boolean,
    val hasNextWeek: Boolean,
)

data class ProgressDto(
    val date: String,
    val dayOfWeek: String,
    val dailyTodoCount: Int,
    val completedDailyTodoCount: Int,
    val isValid: Boolean,
)

fun WeeklyProgressResponse.toData(): WeeklyProgressDto =
    WeeklyProgressDto(
        progressList = this.progress.map { it.toData() },
        hasLastWeek = this.hasLastWeek,
        hasNextWeek = this.hasNextWeek,
    )

fun ProgressResponse.toData(): ProgressDto =
    ProgressDto(
        date = this.date,
        dayOfWeek = this.dayOfWeek,
        dailyTodoCount = this.dailyTodoCount,
        completedDailyTodoCount = this.completedDailyTodoCount,
        isValid = this.isValid,
    )

fun WeeklyProgressDto.toDomain(): WeeklyProgress {
    val target = this.progressList[3]
    val formattedDate = LocalDate.parse(target.date)

    return WeeklyProgress(
        yearMonth = YearMonth.from(formattedDate),
        progressData = this.progressList.map { it.toDomain() },
        hasLastWeek = hasLastWeek,
        hasNextWeek = hasNextWeek,
    )
}

fun ProgressDto.toDomain(): DailyProgress {
    val formattedDate = LocalDate.parse(date)
    val formattedDayOfWeek = this.weekDay()
    if (this.isValid) {
        return DailyProgress.ValidProgress(
            date = formattedDate,
            dayOfWeek = formattedDayOfWeek,
            dailyTodoCount = this.dailyTodoCount,
            completedDailyTodoCount = this.completedDailyTodoCount,
        )
    }
    return DailyProgress.InvalidProgress(
        date = formattedDate,
        dayOfWeek = formattedDayOfWeek,
    )
}

fun ProgressDto.weekDay(): Weekday =
    when (this.dayOfWeek) {
        "SUNDAY" -> Weekday.SUNDAY
        "MONDAY" -> Weekday.MONDAY
        "TUESDAY" -> Weekday.TUESDAY
        "WEDNESDAY" -> Weekday.WEDNESDAY
        "THURSDAY" -> Weekday.THURSDAY
        "FRIDAY" -> Weekday.FRIDAY
        "SATURDAY" -> Weekday.SATURDAY
        else -> throw IllegalArgumentException("Invalid day of week: ${this.dayOfWeek}")
    }
