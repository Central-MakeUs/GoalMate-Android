package cmc.goalmate.data.model

import cmc.goalmate.domain.model.StartedGoal
import cmc.goalmate.remote.dto.response.GoalCreationResponse

data class StartedGoalDto(val newGoalId: Int, val newCommentRoomId: Int)

fun GoalCreationResponse.toData() = StartedGoalDto(newGoalId = menteeGoalId, newCommentRoomId = commentRoomId)

fun StartedGoalDto.toDomain(): StartedGoal = StartedGoal(newGoalId = newGoalId, newCommentRoomId = newCommentRoomId)
