package cmc.goalmate.domain.model

data class UserInfo(
    val id: Int,
    val nickName: String,
    val inProgressGoalCount: Int,
    val completedGoalCount: Int,
    val freeParticipationCount: Int,
    val menteeStatus: String,
)
