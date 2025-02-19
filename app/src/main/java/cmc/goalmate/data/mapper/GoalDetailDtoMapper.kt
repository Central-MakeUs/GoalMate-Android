package cmc.goalmate.data.mapper

import cmc.goalmate.data.model.GoalDetailDto
import cmc.goalmate.data.model.ImageDto
import cmc.goalmate.data.model.MidObjectiveDto
import cmc.goalmate.data.model.WeeklyObjectiveDto
import cmc.goalmate.data.model.convertGoalStatus
import cmc.goalmate.domain.model.GoalDetail
import cmc.goalmate.domain.model.MidObjective
import cmc.goalmate.domain.model.WeeklyObjective
import cmc.goalmate.remote.dto.response.GoalDetailResponse
import cmc.goalmate.remote.dto.response.ImageResponse
import cmc.goalmate.remote.dto.response.MidObjectiveResponse
import cmc.goalmate.remote.dto.response.WeeklyObjectiveResponse

fun GoalDetailResponse.toData(): GoalDetailDto =
    GoalDetailDto(
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
        weeklyObjectives = this.weeklyObjectives.map { it.toData() },
        midObjectives = this.midObjectives.map { it.toData() },
        thumbnailImages = this.thumbnailImages.map { it.toData() },
        contentImages = this.contentImages.map { it.toData() },
    )

fun WeeklyObjectiveResponse.toData(): WeeklyObjectiveDto =
    WeeklyObjectiveDto(
        weekNumber = this.weekNumber,
        description = this.description,
    )

fun MidObjectiveResponse.toData(): MidObjectiveDto =
    MidObjectiveDto(
        sequence = this.sequence,
        description = this.description,
    )

fun ImageResponse.toData(): ImageDto =
    ImageDto(
        sequence = this.sequence,
        imageUrl = this.imageUrl,
    )

fun GoalDetailDto.toDomain(): GoalDetail =
    GoalDetail(
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
        weeklyObjectives = this.weeklyObjectives.map { it.toDomain() },
        midObjectives = this.midObjectives.map { it.toDomain() },
        thumbnailImages = this.thumbnailImages.sortedBy { it.sequence }.map { it.imageUrl },
        contentImages = this.contentImages.sortedBy { it.sequence }.map { it.imageUrl },
    )

fun WeeklyObjectiveDto.toDomain(): WeeklyObjective =
    WeeklyObjective(
        weekNumber = this.weekNumber,
        description = this.description,
    )

fun MidObjectiveDto.toDomain(): MidObjective =
    MidObjective(
        sequence = this.sequence,
        description = this.description,
    )
