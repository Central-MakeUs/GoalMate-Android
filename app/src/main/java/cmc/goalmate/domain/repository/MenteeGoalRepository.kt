package cmc.goalmate.domain.repository

import cmc.goalmate.domain.DataError
import cmc.goalmate.domain.DomainResult
import cmc.goalmate.domain.model.MenteeGoalInfo
import cmc.goalmate.domain.model.MenteeGoals

interface MenteeGoalRepository {
    suspend fun getMenteeGoals(): DomainResult<MenteeGoals, DataError.Network>

    suspend fun getGoalInfo(goalId: Int): DomainResult<MenteeGoalInfo, DataError>
}
