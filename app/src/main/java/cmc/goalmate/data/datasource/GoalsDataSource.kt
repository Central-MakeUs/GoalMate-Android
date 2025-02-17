package cmc.goalmate.data.datasource

import cmc.goalmate.data.model.GoalsDto
import cmc.goalmate.data.model.toData
import cmc.goalmate.data.util.getOrThrow
import cmc.goalmate.remote.service.GoalService
import javax.inject.Inject

class GoalsDataSource
    @Inject
    constructor(private val goalService: GoalService) {
        suspend fun getGoals(): Result<GoalsDto> =
            runCatching {
                goalService.getGoals().getOrThrow().toData()
            }
    }
