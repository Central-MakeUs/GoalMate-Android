package cmc.goalmate.data.model

import cmc.goalmate.domain.model.Goal
import cmc.goalmate.domain.model.GoalStatus
import cmc.goalmate.domain.model.Goals
import cmc.goalmate.remote.dto.response.GoalResponse
import cmc.goalmate.remote.dto.response.GoalsResponse

data class GoalsDto(
    val goals: List<GoalDto>,
)

data class GoalDto(
    val id: Int,
    val title: String,
    val topic: String,
    val description: String,
    val period: Int,
    val dailyDuration: Int,
    val participantsLimit: Int,
    val currentParticipants: Int,
    val isClosingSoon: Boolean,
    val goalStatus: String,
    val mentorName: String,
    val createdAt: String,
    val updatedAt: String,
    val mainImage: String,
)

fun GoalResponse.toData(): GoalDto =
    GoalDto(
        id = this.id,
        title = this.title,
        topic = this.topic,
        description = this.description,
        period = this.period,
        dailyDuration = this.dailyDuration,
        participantsLimit = this.participantsLimit,
        currentParticipants = this.currentParticipants,
        isClosingSoon = this.isClosingSoon,
        goalStatus = this.goalStatus,
        mentorName = this.mentorName,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt,
        mainImage = this.mainImage,
    )

fun GoalsResponse.toData(): GoalsDto = GoalsDto(goals = goals.map { it.toData() })

fun GoalsDto.toDomain(): Goals = Goals(this.goals.map { it.toDomain() })

fun GoalDto.toDomain(): Goal =
    Goal(
        id = this.id,
        title = this.title,
        topic = this.topic,
        description = this.description,
        period = this.period,
        dailyDuration = this.dailyDuration,
        participantsLimit = this.participantsLimit,
        currentParticipants = this.currentParticipants,
        isClosingSoon = this.isClosingSoon,
        goalStatus = convertGoalStatus(this.goalStatus),
        mentorName = this.mentorName,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt,
        mainImage = this.mainImage,
    )

fun convertGoalStatus(status: String): GoalStatus =
    when (status) {
        "NOT_STARTED" -> GoalStatus.NOT_STARTED
        "IN_PROGRESS" -> GoalStatus.IN_PROGRESS
        "CLOSED" -> GoalStatus.CLOSED
        else -> throw IllegalArgumentException("알 수 없는 상태: $status")
    }
