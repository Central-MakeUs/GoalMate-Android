package cmc.goalmate.domain.repository

import cmc.goalmate.domain.DataError
import cmc.goalmate.domain.DomainResult
import cmc.goalmate.domain.NickNameError
import cmc.goalmate.domain.model.UserInfo
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun checkNicknameValidity(nickName: String): DomainResult<Unit, NickNameError>

    suspend fun checkNicknameAvailable(nickName: String): DomainResult<Unit, NickNameError>

    suspend fun updateNickName(nickName: String): DomainResult<Unit, DataError.Network>

    suspend fun getUserInfo(): DomainResult<UserInfo, DataError.Network>

    fun getNickName(): Flow<String>
}
