package cmc.goalmate.domain.model

import java.time.LocalDateTime

data class Comments(val comments: List<Comment>)

data class Comment(
    val id: Int,
    val comment: String,
    val commentedAt: LocalDateTime,
    val writer: String,
    val writerRole: Writer,
)

enum class Writer {
    MENTOR,
    MENTEE,
    ADMIN,
}
