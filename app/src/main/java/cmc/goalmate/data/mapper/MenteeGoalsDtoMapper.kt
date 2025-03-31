package cmc.goalmate.data.mapper

import cmc.goalmate.data.model.MenteeGoalDto
import cmc.goalmate.data.model.MenteeGoalsDto
import cmc.goalmate.domain.model.MenteeGoal
import cmc.goalmate.domain.model.MenteeGoalStatus
import cmc.goalmate.domain.model.MenteeGoals
import cmc.goalmate.remote.dto.response.MenteeGoalResponse
import cmc.goalmate.remote.dto.response.MenteeGoalsResponse
import java.time.LocalDate
import java.time.format.DateTimeFormatter

val goalMateDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

fun MenteeGoalsResponse.toData(): MenteeGoalsDto = MenteeGoalsDto(goals = this.menteeGoals.map { it.toData() })

fun MenteeGoalResponse.toData(): MenteeGoalDto =
    MenteeGoalDto(
        menteeGoalId = id,
        goalId = goalId,
        title = title,
        topic = topic,
        mentorName = mentorName,
        mainImage = mainImage,
        startDate = startDate,
        endDate = endDate,
        finalComment = finalComment,
        todayTodoCount = todayTodoCount,
        todayCompletedCount = todayCompletedCount,
        todayRemainingCount = todayRemainingCount,
        totalTodoCount = totalTodoCount,
        totalCompletedCount = totalCompletedCount,
        commentRoomId = commentRoomId,
        menteeGoalStatus = menteeGoalStatus,
        createdAt = createdAt,
        updatedAt = updatedAt,
    )

fun MenteeGoalsDto.toDomain(): MenteeGoals = MenteeGoals(goals.map { it.toDomain() })

fun MenteeGoalDto.toDomain(
    dateFormatter: DateTimeFormatter = goalMateDateFormatter,
    targetDate: LocalDate = LocalDate.now(),
): MenteeGoal {
    val parsedEndDate = LocalDate.parse(endDate, dateFormatter)
    val goalStatus = if (parsedEndDate < targetDate) {
        MenteeGoalStatus.Completed(finalComment ?: "")
    } else {
        convertMenteeGoalStatus()
    }
    return MenteeGoal(
        menteeGoalId = menteeGoalId,
        goalId = goalId,
        title = title,
        topic = topic,
        mentorName = mentorName,
        mainImage = mainImage,
        startDate = LocalDate.parse(startDate, dateFormatter),
        endDate = parsedEndDate,
        todayTodoCount = todayTodoCount,
        todayCompletedCount = todayCompletedCount,
        todayRemainingCount = todayRemainingCount,
        totalTodoCount = totalTodoCount,
        totalCompletedCount = totalCompletedCount,
        menteeGoalStatus = goalStatus,
        commentRoomId = commentRoomId,
    )
}

private fun MenteeGoalDto.convertMenteeGoalStatus(): MenteeGoalStatus =
    when (menteeGoalStatus) {
        "IN_PROGRESS" -> MenteeGoalStatus.InProgress
        "COMPLETED" -> MenteeGoalStatus.Completed(finalComment ?: "")
        "CANCELED" -> MenteeGoalStatus.Canceled
        else -> MenteeGoalStatus.Unknown
    }
