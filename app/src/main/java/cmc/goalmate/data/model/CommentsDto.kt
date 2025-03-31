package cmc.goalmate.data.model

import cmc.goalmate.data.util.convertUtcToLocal
import cmc.goalmate.domain.model.Comment
import cmc.goalmate.domain.model.Writer
import cmc.goalmate.remote.dto.response.CommentResponse
import cmc.goalmate.remote.dto.response.CommentsResponse

data class CommentsDto(
    val comments: List<CommentDto>,
    val currentPage: Int,
    val nextPage: Int?,
)

data class CommentDto(
    val id: Int,
    val comment: String,
    val commentedAt: String,
    val writer: String,
    val writerRole: String,
)

fun CommentsResponse.toData(): CommentsDto =
    CommentsDto(
        comments = this.comments.map { it.toData() },
        currentPage = this.page.currentPage,
        nextPage = this.page.nextPage,
    )

fun CommentResponse.toData(): CommentDto =
    CommentDto(
        id = id,
        comment = comment,
        commentedAt = commentedAt,
        writer = writer,
        writerRole = writerRole,
    )

fun convertWriterRole(writerRole: String): Writer =
    when (writerRole) {
        "MENTOR" -> Writer.MENTOR
        "MENTEE" -> Writer.MENTEE
        "ADMIN" -> Writer.ADMIN
        else -> error("존재하지 않은 코멘트 작성자 : $writerRole")
    }

fun CommentDto.toDomain(): Comment =
    Comment(
        id = id,
        comment = comment,
        commentedAt = convertUtcToLocal(commentedAt),
        writer = writer,
        writerRole = convertWriterRole(writerRole),
    )
