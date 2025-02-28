package cmc.goalmate.data.model

import cmc.goalmate.domain.model.Comment
import cmc.goalmate.domain.model.Writer
import cmc.goalmate.remote.dto.response.CommentResponse
import cmc.goalmate.remote.dto.response.CommentsResponse
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class CommentsDto(val comments: List<CommentDto>)

data class CommentDto(
    val id: Int,
    val comment: String,
    val commentedAt: String,
    val writer: String,
    val writerRole: String,
)

fun CommentsResponse.toData(): CommentsDto = CommentsDto(comments = this.comments.map { it.toData() })

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

fun CommentDto.toDomain(formatter: DateTimeFormatter = DateTimeFormatter.ISO_DATE_TIME): Comment =
    Comment(
        id = id,
        comment = comment,
        commentedAt = LocalDateTime.parse(commentedAt, formatter),
        writer = writer,
        writerRole = convertWriterRole(writerRole),
    )
