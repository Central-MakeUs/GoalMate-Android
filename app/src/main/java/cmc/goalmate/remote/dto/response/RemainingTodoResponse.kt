package cmc.goalmate.remote.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class RemainingTodoResponse(val hasRemainingTodosToday: Boolean)
