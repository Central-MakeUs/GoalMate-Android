package cmc.goalmate.data.datasource

import cmc.goalmate.data.mapper.toData
import cmc.goalmate.data.model.CommentRoomsDto
import cmc.goalmate.data.util.getOrThrow
import cmc.goalmate.remote.service.CommentService
import javax.inject.Inject

class CommentDataSource
    @Inject
    constructor(private val commentService: CommentService) {
        suspend fun getCommentRooms(): Result<CommentRoomsDto> =
            runCatching {
                commentService.getCommentRooms().getOrThrow().toData()
            }
    }
