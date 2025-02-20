package cmc.goalmate.data.datasource

import cmc.goalmate.data.mapper.toData
import cmc.goalmate.data.model.CommentDto
import cmc.goalmate.data.model.CommentRoomsDto
import cmc.goalmate.data.model.CommentsDto
import cmc.goalmate.data.model.toData
import cmc.goalmate.data.util.getOrThrow
import cmc.goalmate.remote.dto.request.CommentRequest
import cmc.goalmate.remote.service.CommentService
import javax.inject.Inject

class CommentDataSource
    @Inject
    constructor(private val commentService: CommentService) {
        suspend fun getCommentRooms(): Result<CommentRoomsDto> =
            runCatching {
                commentService.getCommentRooms().getOrThrow().toData()
            }

        suspend fun getComments(roomId: Int): Result<CommentsDto> =
            runCatching {
                commentService.getComments(roomId).getOrThrow().toData()
            }

        suspend fun postComment(
            roomId: Int,
            contents: String,
        ): Result<CommentDto> =
            runCatching {
                val request = CommentRequest(contents)
                commentService.postComment(roomId, request).getOrThrow().toData()
            }

        suspend fun updateComment(
            commentId: Int,
            contents: String,
        ): Result<CommentDto> =
            runCatching {
                val request = CommentRequest(contents)
                commentService.updateComment(commentId, request).getOrThrow().toData()
            }

        suspend fun deleteComment(commentId: Int): Result<Unit> =
            runCatching {
                commentService.deleteComment(commentId).getOrThrow()
            }
    }
