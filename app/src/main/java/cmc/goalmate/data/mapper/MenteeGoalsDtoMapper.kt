package cmc.goalmate.data.mapper

import cmc.goalmate.data.model.MenteeGoalDto
import cmc.goalmate.data.model.MenteeGoalsDto
import cmc.goalmate.domain.model.MenteeGoal
import cmc.goalmate.domain.model.MenteeGoalStatus
import cmc.goalmate.domain.model.MenteeGoalStatus.CANCELED
import cmc.goalmate.domain.model.MenteeGoalStatus.COMPLETED
import cmc.goalmate.domain.model.MenteeGoalStatus.IN_PROGRESS
import cmc.goalmate.domain.model.MenteeGoalStatus.UNKNOWN
import cmc.goalmate.domain.model.MenteeGoals
import cmc.goalmate.remote.dto.response.MenteeGoalResponse
import cmc.goalmate.remote.dto.response.MenteeGoalsResponse
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun MenteeGoalsResponse.toData(): MenteeGoalsDto = MenteeGoalsDto(goals = this.menteeGoals.map { it.toData() })

fun MenteeGoalResponse.toData(): MenteeGoalDto =
    MenteeGoalDto(
        id = id,
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
        menteeGoalStatus = menteeGoalStatus,
        createdAt = createdAt,
        updatedAt = updatedAt,
    )

fun MenteeGoalsDto.toDomain(): MenteeGoals = MenteeGoals(goals.map { it.toDomain() })

fun MenteeGoalDto.toDomain(dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")): MenteeGoal =
    MenteeGoal(
        id = id,
        title = title,
        topic = topic,
        mentorName = mentorName,
        mainImage = mainImage,
        startDate = LocalDate.parse(startDate, dateFormatter),
        endDate = LocalDate.parse(endDate, dateFormatter),
        todayTodoCount = todayTodoCount,
        todayCompletedCount = todayCompletedCount,
        todayRemainingCount = todayRemainingCount,
        totalTodoCount = totalTodoCount,
        totalCompletedCount = totalCompletedCount,
        menteeGoalStatus = convertMenteeGoalStatus(menteeGoalStatus),
    )

private fun convertMenteeGoalStatus(status: String): MenteeGoalStatus =
    when (status) {
        "IN_PROGRESS" -> IN_PROGRESS
        "COMPLETED" -> COMPLETED
        "CANCELED" -> CANCELED
        else -> UNKNOWN
    }
