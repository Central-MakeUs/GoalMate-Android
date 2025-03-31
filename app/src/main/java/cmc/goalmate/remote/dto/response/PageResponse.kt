package cmc.goalmate.remote.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PageResponse(
    @SerialName("totalElements") val totalElements: Int,
    @SerialName("totalPages") val totalPages: Int,
    @SerialName("currentPage") val currentPage: Int,
    @SerialName("pageSize") val pageSize: Int,
    @SerialName("nextPage") val nextPage: Int?,
    @SerialName("prevPage") val prevPage: Int?,
    @SerialName("hasNext") val hasNext: Boolean,
    @SerialName("hasPrev") val hasPrev: Boolean,
)
