package cmc.goalmate.domain.repository

import cmc.goalmate.domain.DataError
import cmc.goalmate.domain.DomainResult
import cmc.goalmate.domain.model.CommentRooms

interface CommentRepository {
    suspend fun getCommentRooms(): DomainResult<CommentRooms, DataError.Network>
}
