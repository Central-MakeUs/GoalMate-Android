package cmc.goalmate.data.repository

import cmc.goalmate.data.datasource.CommentDataSource
import cmc.goalmate.data.mapper.toDataError
import cmc.goalmate.data.mapper.toDomain
import cmc.goalmate.data.model.toDomain
import cmc.goalmate.domain.DataError
import cmc.goalmate.domain.DomainResult
import cmc.goalmate.domain.model.CommentRooms
import cmc.goalmate.domain.model.Comments
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

        override suspend fun getComments(roomId: Int): DomainResult<Comments, DataError.Network> =
            commentDataSource.getComments(roomId = roomId).fold(
                onSuccess = { commentsDto ->
                    val result = commentsDto.comments.map { it.toDomain() }.sortedBy { it.commentedAt }
                    DomainResult.Success(Comments(result))
                },
                onFailure = { DomainResult.Error(it.toDataError()) },
            )

        override suspend fun postComment(
            roomId: Int,
            content: String,
        ): DomainResult<Int, DataError.Network> =
            commentDataSource.postComment(roomId, content).fold(
                onSuccess = {
                    DomainResult.Success(it.id)
                },
                onFailure = {
                    DomainResult.Error(it.toDataError())
                },
            )

        override suspend fun updateComment(
            commentId: Int,
            content: String,
        ): DomainResult<Int, DataError.Network> =
            commentDataSource.updateComment(commentId, content).fold(
                onSuccess = {
                    DomainResult.Success(it.id)
                },
                onFailure = {
                    DomainResult.Error(it.toDataError())
                },
            )

        override suspend fun deleteComment(commentId: Int): DomainResult<Unit, DataError.Network> =
            commentDataSource.deleteComment(commentId).fold(
                onSuccess = {
                    DomainResult.Success(Unit)
                },
                onFailure = {
                    DomainResult.Error(it.toDataError())
                },
            )
    }
