package cmc.goalmate.data.datasource

import cmc.goalmate.data.mapper.toData
import cmc.goalmate.data.model.MenteeGoalsDto
import cmc.goalmate.data.util.getOrThrow
import cmc.goalmate.remote.service.MenteeGoalService
import javax.inject.Inject

class MenteeGoalDataSource
    @Inject
    constructor(private val menteeGoalService: MenteeGoalService) {
        suspend fun getGoals(): Result<MenteeGoalsDto> =
            runCatching {
                menteeGoalService.getMenteeGoals().getOrThrow().toData()
            }
    }
