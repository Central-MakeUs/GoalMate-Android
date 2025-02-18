package cmc.goalmate.data.repository

import cmc.goalmate.data.datasource.CommentDataSource
import cmc.goalmate.data.mapper.toDataError
import cmc.goalmate.data.mapper.toDomain
import cmc.goalmate.domain.DataError
import cmc.goalmate.domain.DomainResult
import cmc.goalmate.domain.model.CommentRooms
import cmc.goalmate.domain.repository.CommentRepository
import javax.inject.Inject

class CommentRepositoryImpl
    @Inject
    constructor(private val commentDataSource: CommentDataSource) : CommentRepository {
        override suspend fun getCommentRooms(): DomainResult<CommentRooms, DataError.Network> =
            commentDataSource.getCommentRooms().fold(
                onSuccess = { DomainResult.Success(it.toDomain()) },
                onFailure = { DomainResult.Error(it.toDataError()) },
            )
    }
