package cmc.goalmate.domain.repository

import cmc.goalmate.domain.DataError
import cmc.goalmate.domain.DomainResult

interface UserRepository {
    suspend fun isNicknameAvailable(nickName: String): DomainResult<Boolean, DataError.Network>

    suspend fun updateNickName(nickName: String): DomainResult<Unit, DataError.Network>
}
