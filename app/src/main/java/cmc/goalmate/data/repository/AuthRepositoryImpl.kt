package cmc.goalmate.data.repository

import cmc.goalmate.data.datasource.AuthDataSource
import cmc.goalmate.data.datasource.LocalUserDataSource
import cmc.goalmate.data.datasource.TokenDataSource
import cmc.goalmate.data.mapper.toDataError
import cmc.goalmate.data.model.toDomain
import cmc.goalmate.domain.DataError
import cmc.goalmate.domain.DomainResult
import cmc.goalmate.domain.model.Login
import cmc.goalmate.domain.model.Token
import cmc.goalmate.domain.model.toData
import cmc.goalmate.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AuthRepositoryImpl
    @Inject
    constructor(
        private val authDataSource: AuthDataSource,
        private val tokenDataSource: TokenDataSource,
        private val localUserDataSource: LocalUserDataSource,
    ) : AuthRepository {
        override suspend fun login(idToken: String): DomainResult<Login, DataError.Network> =
            authDataSource.login(idToken).fold(
                onSuccess = { login ->
                    DomainResult.Success(login.toDomain())
                },
                onFailure = { error ->
                    DomainResult.Error(error.toDataError())
                },
            )

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

        override suspend fun validateToken(): DomainResult<Unit, DataError> =
            authDataSource.validateToken()
                .fold(
                    onSuccess = {
                        DomainResult.Success(Unit)
                    },
                    onFailure = {
                        if (it.toDataError() == DataError.Network.UNAUTHORIZED) {
                            handleTokenReissue()
                        } else {
                            DomainResult.Error(it.toDataError())
                        }
                    },
                )

        private suspend fun handleTokenReissue(): DomainResult<Unit, DataError> {
            val refreshToken = tokenDataSource.getToken().first().refreshToken
            return authDataSource.postReissue(refreshToken)
                .fold(
                    onSuccess = { tokenDto ->
                        saveToken(tokenDto.toDomain())
                    },
                    onFailure = {
                        DomainResult.Error(DataError.Network.UNAUTHORIZED)
                    },
                )
        }

        override suspend fun logout(): DomainResult<Unit, DataError> =
            authDataSource.logout().fold(
                onSuccess = { deleteToken() },
                onFailure = {
                    DomainResult.Error(it.toDataError())
                },
            )

        override suspend fun deleteAccount(): DomainResult<Unit, DataError> =
            authDataSource.deleteAccount().fold(
                onSuccess = {
                    localUserDataSource.deleteNickName()
                    deleteToken()
                },
                onFailure = {
                    DomainResult.Error(it.toDataError())
                },
            )
    }
