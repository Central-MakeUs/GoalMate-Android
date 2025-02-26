package cmc.goalmate.domain

import cmc.goalmate.data.model.ProgressDto
import cmc.goalmate.data.model.toDomain
import cmc.goalmate.domain.model.DailyProgress
import cmc.goalmate.domain.model.GoalMateCalendar
import cmc.goalmate.domain.model.Week
import cmc.goalmate.domain.model.Weekday
import java.time.DayOfWeek
import java.time.LocalDate

// fun generateWeeklyCalendar(
//    startDate: LocalDate,
//    endDate: LocalDate,
//    target: LocalDate,
// ): GoalMateCalendar {
//    val weeks = mutableListOf<List<DailyProgress>>()
//    var currentDate = startDate
//    var weekNumber = 1
//    var targetDateWeekNumber = -1
//
//    while (currentDate.dayOfWeek != DayOfWeek.SUNDAY) {
//        currentDate = currentDate.minusDays(1)
//    }
//
//    while (currentDate <= endDate || currentDate.dayOfWeek != DayOfWeek.SUNDAY) {
//        val weekProgress = mutableListOf<DailyProgress>()
//
//        for (dayOffset in 0..6) {
//            val currentDay = currentDate.plusDays(dayOffset.toLong())
//            val dayOfWeek = currentDay.toDomain()
//            val progress = if (currentDay in startDate..endDate) {
//                DailyProgress.ValidProgress(
//                    date = currentDay,
//                    dayOfWeek = dayOfWeek,
//                    dailyTodoCount = 0,
//                    completedDailyTodoCount = 0,
//                )
//            } else {
//                DailyProgress.InvalidProgress(date = currentDay, dayOfWeek = dayOfWeek)
//            }
//            weekProgress.add(progress)
//
//            if (currentDay == target && targetDateWeekNumber == -1) {
//                targetDateWeekNumber = weekNumber
//            }
//        }
//        weeks.add(weekProgress)
//
//        currentDate = currentDate.plusWeeks(1)
//        weekNumber++
//    }
//
//    return GoalMateCalendar(
//        weeklyData = weeks,
//        weekNumber = targetDateWeekNumber,
//        shouldLoadPrevious = targetDateWeekNumber > 1,
//    )
// }

fun generateWeeklyCalendar(
    startDate: LocalDate,
    endDate: LocalDate,
    target: LocalDate,
): GoalMateCalendar {
    val weeks = mutableListOf<Week>()
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

        val shouldLoadPrevious = weekNumber > 1
        weeks.add(Week(dailyProgresses = weekProgress, shouldLoadPrevious = shouldLoadPrevious))

        currentDate = currentDate.plusWeeks(1)
        weekNumber++
    }

    weeks.forEachIndexed { index, week ->
        if (index + 1 > targetDateWeekNumber) {
            weeks[index] = week.copy(shouldLoadPrevious = false)
        }
    }

    return GoalMateCalendar(
        weeklyData = weeks,
        weekNumber = targetDateWeekNumber,
    )
}

fun updateProgressForWeeks(
    weeks: List<Week>,
    updatedWeekProgressDtos: List<ProgressDto>,
): List<Week> {
    if (updatedWeekProgressDtos.isEmpty()) return weeks

    val updatedWeekProgress = updatedWeekProgressDtos.map { it.toDomain() }
    val weekIndex = weeks.indexOfFirst { it.dailyProgresses.first().date == updatedWeekProgress.first().date }

    val updatedCalendar = weeks.toMutableList()
    if (weekIndex != -1) {
        updatedCalendar[weekIndex] = Week(dailyProgresses = updatedWeekProgress, shouldLoadPrevious = true)

        if (weekIndex + 1 < updatedCalendar.size) {
            val nextWeek = updatedCalendar[weekIndex + 1]
            updatedCalendar[weekIndex + 1] = nextWeek.copy(shouldLoadPrevious = false)
        }
    }
    return updatedCalendar
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
