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
    constructor(
        private val commentDataSource: CommentDataSource,
    ) : CommentRepository {
        private var commentsPageState =
            PagingState<Comments>(
                currentPage = 1,
                nextPage = null,
            )

        override suspend fun getCommentRooms(): DomainResult<CommentRooms, DataError.Network> =
            commentDataSource.getCommentRooms().fold(
                onSuccess = {
                    DomainResult.Success(it.toDomain())
                },
                onFailure = { DomainResult.Error(it.toDataError()) },
            )

        override suspend fun getComments(
            roomId: Int,
            targetPage: Int?,
        ): DomainResult<Comments, DataError.Network> {
            if (targetPage == commentsPageState.currentPage || !commentsPageState.hasNextPage) {
                Comments(comments = listOf(), nextPage = commentsPageState.nextPage)
            }
            return commentDataSource
                .getComments(
                    roomId = roomId,
                    targetPage = commentsPageState.nextPage,
                ).fold(
                    onSuccess = { commentsDto ->
                        val newComments = commentsDto.comments.map { it.toDomain() }.sortedByDescending { it.commentedAt }

                        commentsPageState = commentsPageState.copy(currentPage = commentsDto.currentPage, nextPage = commentsDto.nextPage)

                        val result = Comments(comments = newComments, nextPage = commentsPageState.nextPage)
                        DomainResult.Success(result)
                    },
                    onFailure = { DomainResult.Error(it.toDataError()) },
                )
        }

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

        override suspend fun getNewCommentCount(): DomainResult<Int, DataError.Network> =
            commentDataSource.getNewCommentCount().fold(
                onSuccess = {
                    DomainResult.Success(it)
                },
                onFailure = {
                    DomainResult.Error(it.toDataError())
                },
            )
    }

data class PagingState<T>(
    val currentPage: Int = 1,
    val nextPage: Int? = null,
) {
    val hasNextPage: Boolean
        get() = nextPage != null
}
