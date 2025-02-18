package cmc.goalmate.data.repository

import cmc.goalmate.data.datasource.MenteeGoalDataSource
import cmc.goalmate.data.mapper.toDataError
import cmc.goalmate.data.mapper.toDomain
import cmc.goalmate.domain.DataError
import cmc.goalmate.domain.DomainResult
import cmc.goalmate.domain.model.MenteeGoals
import cmc.goalmate.domain.repository.MenteeGoalRepository
import javax.inject.Inject

class MenteeGoalRepositoryImpl
    @Inject
    constructor(private val menteeGoalDataSource: MenteeGoalDataSource) :
    MenteeGoalRepository {
        override suspend fun getMenteeGoals(): DomainResult<MenteeGoals, DataError.Network> =
            menteeGoalDataSource.getGoals().fold(
                onSuccess = {
                    DomainResult.Success(it.toDomain())
                },
                onFailure = {
                    DomainResult.Error(it.toDataError())
                },
            )
    }
