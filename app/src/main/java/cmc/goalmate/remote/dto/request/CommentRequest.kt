package cmc.goalmate.remote.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CommentRequest(
    @SerialName("comment") val comment: String,
)
