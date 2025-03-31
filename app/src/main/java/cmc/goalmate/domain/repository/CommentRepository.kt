package cmc.goalmate.domain.repository

import cmc.goalmate.domain.DataError
import cmc.goalmate.domain.DomainResult
import cmc.goalmate.domain.model.CommentRooms
import cmc.goalmate.domain.model.Comments

interface CommentRepository {
    suspend fun getCommentRooms(): DomainResult<CommentRooms, DataError.Network>

    suspend fun getComments(
        roomId: Int,
        targetPage: Int?,
    ): DomainResult<Comments, DataError.Network>

    suspend fun postComment(
        roomId: Int,
        content: String,
    ): DomainResult<Int, DataError.Network>

    suspend fun updateComment(
        commentId: Int,
        content: String,
    ): DomainResult<Int, DataError.Network>

    suspend fun deleteComment(commentId: Int): DomainResult<Unit, DataError.Network>

    suspend fun getNewCommentCount(): DomainResult<Int, DataError.Network>
}
