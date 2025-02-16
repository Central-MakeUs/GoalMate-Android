package cmc.goalmate.remote.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class BaseResponse<T>(
    val status: String,
    val code: String,
    val message: String,
    val data: T,
)
