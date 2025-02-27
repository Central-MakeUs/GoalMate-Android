package cmc.goalmate.domain.model

import java.time.LocalDate
import java.time.temporal.ChronoUnit

data class MenteeGoalInfo(
    val id: Int,
    val title: String,
    val topic: String,
    val mentorName: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val totalTodoCount: Int,
    val totalCompletedCount: Int,
    val menteeGoalStatus: MenteeGoalStatus,
) {
    val period: Int = ChronoUnit.DAYS.between(startDate, endDate).toInt()
}

fun MenteeGoal.toInfo(): MenteeGoalInfo =
    MenteeGoalInfo(
        id = menteeGoalId,
        title = title,
        topic = topic,
        mentorName = mentorName,
        startDate = startDate,
        endDate = endDate,
        totalTodoCount = totalTodoCount,
        totalCompletedCount = totalCompletedCount,
        menteeGoalStatus = menteeGoalStatus,
    )
