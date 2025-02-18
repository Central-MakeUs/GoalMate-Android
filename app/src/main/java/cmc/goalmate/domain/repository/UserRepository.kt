package cmc.goalmate.domain.repository

import cmc.goalmate.domain.DataError
import cmc.goalmate.domain.DomainResult
import cmc.goalmate.domain.model.UserInfo

interface UserRepository {
    suspend fun isNicknameAvailable(nickName: String): DomainResult<Boolean, DataError.Network>

    suspend fun updateNickName(nickName: String): DomainResult<Unit, DataError.Network>

    suspend fun getUserInfo(): DomainResult<UserInfo, DataError.Network>
}
