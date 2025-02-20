package cmc.goalmate.remote.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TodoRequest(
    @SerialName("todo_status")val todoStatus: String,
)
