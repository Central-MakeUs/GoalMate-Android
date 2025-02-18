package cmc.goalmate.data.repository

import cmc.goalmate.data.datasource.UserDataSource
import cmc.goalmate.data.mapper.toDataError
import cmc.goalmate.data.model.toDomain
import cmc.goalmate.domain.DataError
import cmc.goalmate.domain.DomainResult
import cmc.goalmate.domain.model.UserInfo
import cmc.goalmate.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl
    @Inject
    constructor(private val userDataSource: UserDataSource) : UserRepository {
        override suspend fun isNicknameAvailable(nickName: String): DomainResult<Boolean, DataError.Network> =
            userDataSource.validateNickName(nickName).fold(
                onSuccess = { result ->
                    DomainResult.Success(result)
                },
                onFailure = { error ->
                    DomainResult.Error(error.toDataError())
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
