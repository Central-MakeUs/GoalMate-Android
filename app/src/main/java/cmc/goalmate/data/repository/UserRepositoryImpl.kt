package cmc.goalmate.data.repository

import cmc.goalmate.data.datasource.UserDataSource
import cmc.goalmate.data.mapper.toDataError
import cmc.goalmate.data.model.toDomain
import cmc.goalmate.domain.DataError
import cmc.goalmate.domain.DomainResult
import cmc.goalmate.domain.NickNameError
import cmc.goalmate.domain.ValidateNickName
import cmc.goalmate.domain.model.UserInfo
import cmc.goalmate.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl
    @Inject
    constructor(
        private val userDataSource: UserDataSource,
        private val validateNickName: ValidateNickName,
    ) : UserRepository {
        override fun checkNicknameValidity(nickName: String): DomainResult<Unit, NickNameError> = validateNickName(nickName)

        override suspend fun checkNicknameAvailable(nickName: String): DomainResult<Unit, NickNameError> =
            userDataSource.validateNickName(nickName).fold(
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
            userDataSource.updateNickName(nickName).fold(
                onSuccess = { DomainResult.Success(Unit) },
                onFailure = { error ->
                    DomainResult.Error(error.toDataError())
                },
            )

        override suspend fun getUserInfo(): DomainResult<UserInfo, DataError.Network> =
            userDataSource.getUserInfo().fold(
                onSuccess = { userInfo ->
                    DomainResult.Success(userInfo.toDomain())
                },
                onFailure = { error ->
                    DomainResult.Error(error.toDataError())
                },
            )
    }
