package cmc.goalmate

import cmc.goalmate.data.model.ProgressDto
import cmc.goalmate.data.model.WeeklyProgressDto
import cmc.goalmate.domain.generateWeeklyCalendar
import cmc.goalmate.domain.updateProgressForWeeks
import org.junit.Test
import java.time.LocalDate

class CalendarTest {
    @Test
    fun test01() {
        val startDate = LocalDate.of(2025, 2, 10)
        val endDate = LocalDate.of(2025, 3, 14)
        val target = LocalDate.of(2025, 2, 20)
        val results = generateWeeklyCalendar(startDate, endDate, target)

        assert(results.weekNumber == 2)

        results.weeklyData.forEach {
            println(it)
            println()
        }

        println("updated!")
        val result2 = updateProgressForWeeks(results.weeklyData, weeklyProgress2.progressList)
        result2.forEach {
            println(it)
            println()
        }
    }
}

val weeklyProgress2 = WeeklyProgressDto(
    progressList = listOf(
        ProgressDto("2025-02-16", "SUNDAY", 3, 2, true),
        ProgressDto("2025-02-17", "MONDAY", 5, 3, true),
        ProgressDto("2025-02-18", "TUESDAY", 2, 1, true),
        ProgressDto("2025-02-19", "WEDNESDAY", 4, 3, true),
        ProgressDto("2025-02-20", "THURSDAY", 3, 2, true),
        ProgressDto("2025-02-21", "FRIDAY", 4, 3, true),
        ProgressDto("2025-02-22", "SATURDAY", 2, 1, true),
    ),
    hasLastWeek = true,
    hasNextWeek = true,
)
