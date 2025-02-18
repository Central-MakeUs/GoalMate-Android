package cmc.goalmate.data.model

import cmc.goalmate.domain.model.UserInfo
import cmc.goalmate.remote.dto.response.UserInfoResponse

data class UserInfoDto(
    val id: Int,
    val name: String?,
    val inProgressGoalCount: Int,
    val completedGoalCount: Int,
    val freeParticipationCount: Int,
    val menteeStatus: String,
)

fun UserInfoResponse.toData(): UserInfoDto =
    UserInfoDto(
        id = this.id,
        name = this.name,
        inProgressGoalCount = this.inProgressGoalCount,
        completedGoalCount = this.completedGoalCount,
        freeParticipationCount = this.freeParticipationCount,
        menteeStatus = this.menteeStatus,
    )

fun UserInfoDto.toDomain(): UserInfo =
    UserInfo(
        id = this.id,
        nickName = requireNotNull(this.name) { "닉네임이 설정되어있지 않습니다." },
        inProgressGoalCount = this.inProgressGoalCount,
        completedGoalCount = this.completedGoalCount,
        freeParticipationCount = this.freeParticipationCount,
        menteeStatus = this.menteeStatus,
    )
