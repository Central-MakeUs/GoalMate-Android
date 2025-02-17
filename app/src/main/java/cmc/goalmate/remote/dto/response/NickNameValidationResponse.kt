package cmc.goalmate.remote.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NickNameValidationResponse(
    @SerialName("is_available") val isAvailable: Boolean,
)
