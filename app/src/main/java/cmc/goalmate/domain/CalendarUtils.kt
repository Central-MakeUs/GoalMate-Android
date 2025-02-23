package cmc.goalmate.domain

import cmc.goalmate.data.model.WeeklyProgressDto
import cmc.goalmate.data.model.toDomain
import cmc.goalmate.domain.model.DailyProgress
import cmc.goalmate.domain.model.GoalMateCalendar
import cmc.goalmate.domain.model.Weekday
import java.time.DayOfWeek
import java.time.LocalDate

fun generateWeeklyCalendar(
    startDate: LocalDate,
    endDate: LocalDate,
    target: LocalDate,
): GoalMateCalendar {
    val weeks = mutableListOf<List<DailyProgress>>()
    var currentDate = startDate
    var weekNumber = 1
    var targetDateWeekNumber = -1

    while (currentDate.dayOfWeek != DayOfWeek.SUNDAY) {
        currentDate = currentDate.minusDays(1)
    }

    while (currentDate <= endDate || currentDate.dayOfWeek != DayOfWeek.SUNDAY) {
        val weekProgress = mutableListOf<DailyProgress>()

        for (dayOffset in 0..6) {
            val currentDay = currentDate.plusDays(dayOffset.toLong())
            val dayOfWeek = currentDay.toDomain()
            val progress = if (currentDay in startDate..endDate) {
                DailyProgress.ValidProgress(
                    date = currentDay,
                    dayOfWeek = dayOfWeek,
                    dailyTodoCount = 0,
                    completedDailyTodoCount = 0,
                )
            } else {
                DailyProgress.InvalidProgress(date = currentDay, dayOfWeek = dayOfWeek)
            }
            weekProgress.add(progress)

            if (currentDay == target && targetDateWeekNumber == -1) {
                targetDateWeekNumber = weekNumber
            }
        }
        weeks.add(weekProgress)

        currentDate = currentDate.plusWeeks(1)
        weekNumber++
    }

    return GoalMateCalendar(
        weeklyData = weeks,
        weekNumber = targetDateWeekNumber,
        shouldLoadPrevious = targetDateWeekNumber > 1,
    )
}

fun updateProgressForWeeks(
    weeks: List<List<DailyProgress>>,
    updatedWeeklyDataDto: WeeklyProgressDto,
): List<List<DailyProgress>> {
    if (updatedWeeklyDataDto.progressList.isEmpty()) return weeks

    val updated = updatedWeeklyDataDto.toDomain()
    val updatedStartDate = updated.progressData.first()

    val mutableWeeks = weeks.toMutableList()

    val weekIndex = weeks.indexOfFirst { it.first().date == updatedStartDate.date }

    if (weekIndex != -1) {
        val updatedWeek = updatedWeeklyDataDto.progressList.map { progress ->
            val date = LocalDate.parse(progress.date)
            val dayOfWeek = date.toDomain()

            if (progress.isValid) {
                DailyProgress.ValidProgress(
                    date = date,
                    dayOfWeek = dayOfWeek,
                    dailyTodoCount = progress.dailyTodoCount,
                    completedDailyTodoCount = progress.completedDailyTodoCount,
                )
            } else {
                DailyProgress.InvalidProgress(
                    date = date,
                    dayOfWeek = dayOfWeek,
                )
            }
        }

        mutableWeeks[weekIndex] = updatedWeek
    }

    return mutableWeeks
}

fun LocalDate.toDomain(): Weekday =
    when (this.dayOfWeek) {
        DayOfWeek.SUNDAY -> Weekday.SUNDAY
        DayOfWeek.MONDAY -> Weekday.MONDAY
        DayOfWeek.TUESDAY -> Weekday.TUESDAY
        DayOfWeek.WEDNESDAY -> Weekday.WEDNESDAY
        DayOfWeek.THURSDAY -> Weekday.THURSDAY
        DayOfWeek.FRIDAY -> Weekday.FRIDAY
        DayOfWeek.SATURDAY -> Weekday.SATURDAY
        else -> error("invalid weekDay : ${this.dayOfWeek}")
    }
