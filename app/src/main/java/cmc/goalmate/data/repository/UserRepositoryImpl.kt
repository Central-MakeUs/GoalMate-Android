package cmc.goalmate.data.repository

import cmc.goalmate.data.datasource.LocalUserDataSource
import cmc.goalmate.data.datasource.RemoteUserDataSource
import cmc.goalmate.data.mapper.toDataError
import cmc.goalmate.data.model.toDomain
import cmc.goalmate.domain.DataError
import cmc.goalmate.domain.DomainResult
import cmc.goalmate.domain.NickNameError
import cmc.goalmate.domain.ValidateNickName
import cmc.goalmate.domain.model.UserInfo
import cmc.goalmate.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepositoryImpl
    @Inject
    constructor(
        private val localUserDataSource: LocalUserDataSource,
        private val remoteUserDataSource: RemoteUserDataSource,
        private val validateNickName: ValidateNickName,
    ) : UserRepository {
        override fun checkNicknameValidity(nickName: String): DomainResult<Unit, NickNameError> = validateNickName(nickName)

        override suspend fun checkNicknameAvailable(nickName: String): DomainResult<Unit, NickNameError> =
            remoteUserDataSource.validateNickName(nickName).fold(
                onSuccess = { result ->
                    if (result) {
                        DomainResult.Success(Unit)
                    } else {
                        DomainResult.Error(NickNameError.DUPLICATED)
                    }
                },
                onFailure = {
                    DomainResult.Error(NickNameError.NETWORK_ERROR)
                },
            )

        override suspend fun updateNickName(nickName: String): DomainResult<Unit, DataError.Network> =
            remoteUserDataSource.updateNickName(nickName).fold(
                onSuccess = {
                    localUserDataSource.saveNickName(nickName)
                    DomainResult.Success(Unit)
                },
                onFailure = { error ->
                    DomainResult.Error(error.toDataError())
                },
            )

        override suspend fun getUserInfo(): DomainResult<UserInfo, DataError.Network> =
            remoteUserDataSource.getUserInfo().fold(
                onSuccess = { userInfoDto ->
                    val userInfo = userInfoDto.toDomain()
                    localUserDataSource.saveNickName(userInfo.nickName)
                    DomainResult.Success(userInfo)
                },
                onFailure = { error ->
                    DomainResult.Error(error.toDataError())
                },
            )

        override fun getNickName(): Flow<String> = localUserDataSource.getNickName()
    }
