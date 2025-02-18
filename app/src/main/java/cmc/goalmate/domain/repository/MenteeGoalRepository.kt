package cmc.goalmate.domain.repository

import cmc.goalmate.domain.DataError
import cmc.goalmate.domain.DomainResult
import cmc.goalmate.domain.model.MenteeGoals

interface MenteeGoalRepository {
    suspend fun getMenteeGoals(): DomainResult<MenteeGoals, DataError.Network>
}
