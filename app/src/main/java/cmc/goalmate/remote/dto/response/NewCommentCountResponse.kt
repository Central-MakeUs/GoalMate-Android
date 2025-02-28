package cmc.goalmate.remote.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NewCommentCountResponse(
    @SerialName("new_comments_count") val count: Int,
)
