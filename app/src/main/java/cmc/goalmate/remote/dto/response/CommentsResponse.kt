package cmc.goalmate.remote.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CommentsResponse(
    val comments: List<CommentResponse>,
    val page: PageResponse,
)

@Serializable
data class CommentResponse(
    val id: Int,
    val comment: String,
    @SerialName("commented_at") val commentedAt: String,
    @SerialName("writer") val writer: String,
    @SerialName("writer_role") val writerRole: String,
)
