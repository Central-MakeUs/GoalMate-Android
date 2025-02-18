package cmc.goalmate.data.repository

import cmc.goalmate.data.datasource.GoalsDataSource
import cmc.goalmate.data.mapper.toDataError
import cmc.goalmate.data.mapper.toDomain
import cmc.goalmate.data.model.toDomain
import cmc.goalmate.domain.DataError
import cmc.goalmate.domain.DomainResult
import cmc.goalmate.domain.model.GoalDetail
import cmc.goalmate.domain.model.Goals
import cmc.goalmate.domain.repository.GoalsRepository
import javax.inject.Inject

class GoalsRepositoryImpl
    @Inject
    constructor(private val goalsDataSource: GoalsDataSource) : GoalsRepository {
        override suspend fun getGoals(): DomainResult<Goals, DataError.Network> =
            goalsDataSource.getGoals().fold(
                onSuccess = { goals ->
                    DomainResult.Success(goals.toDomain())
                },
                onFailure = { DomainResult.Error(it.toDataError()) },
            )

        override suspend fun getGoalDetail(goalId: Int): DomainResult<GoalDetail, DataError.Network> =
            goalsDataSource.getGoalDetail(goalId).fold(
                onSuccess = { goalDetail ->
                    DomainResult.Success(goalDetail.toDomain())
                },
                onFailure = {
                    DomainResult.Error(it.toDataError())
                },
            )
    }
