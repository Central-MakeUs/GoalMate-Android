package cmc.goalmate.data.repository

import cmc.goalmate.data.datasource.TokenDataSource
import cmc.goalmate.domain.DataError
import cmc.goalmate.domain.DomainResult
import cmc.goalmate.domain.model.Token
import cmc.goalmate.domain.model.toData
import cmc.goalmate.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserRepositoryImpl
    @Inject
    constructor(private val tokenDataSource: TokenDataSource) : UserRepository {
        override suspend fun isLogin(): Flow<Boolean> =
            tokenDataSource.getToken().map { token ->
                token.accessToken.isNotBlank() && token.refreshToken.isNotBlank()
            }

        override suspend fun saveToken(token: Token): DomainResult<Unit, DataError.Local> =
            tokenDataSource.saveToken(
                token.toData(),
            ).fold(
                onSuccess = { DomainResult.Success(Unit) },
                onFailure = { DomainResult.Error(DataError.Local.IO_ERROR) },
            )

        override suspend fun deleteToken(): DomainResult<Unit, DataError.Local> =
            tokenDataSource.deleteToken().fold(
                onSuccess = { DomainResult.Success(Unit) },
                onFailure = { DomainResult.Error(DataError.Local.IO_ERROR) },
            )
    }
