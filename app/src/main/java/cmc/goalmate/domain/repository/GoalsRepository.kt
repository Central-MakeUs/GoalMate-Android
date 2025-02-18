package cmc.goalmate.domain.repository

import cmc.goalmate.domain.DataError
import cmc.goalmate.domain.DomainResult
import cmc.goalmate.domain.model.GoalDetail
import cmc.goalmate.domain.model.Goals

interface GoalsRepository {
    suspend fun getGoals(): DomainResult<Goals, DataError.Network>

    suspend fun getGoalDetail(goalId: Int): DomainResult<GoalDetail, DataError.Network>
}
