package cmc.goalmate.domain.repository

import cmc.goalmate.domain.DataError
import cmc.goalmate.domain.DomainResult
import cmc.goalmate.domain.model.CommentRooms
import cmc.goalmate.domain.model.Comments

interface CommentRepository {
    suspend fun getCommentRooms(): DomainResult<CommentRooms, DataError.Network>

    suspend fun getComments(roomId: Int): DomainResult<Comments, DataError.Network>
}
