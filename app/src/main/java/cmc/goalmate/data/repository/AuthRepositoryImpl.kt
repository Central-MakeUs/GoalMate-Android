package cmc.goalmate.data.repository

import cmc.goalmate.data.datasource.AuthDataSource
import cmc.goalmate.data.exception.HttpException
import cmc.goalmate.data.exception.NetworkException
import cmc.goalmate.data.exception.UnknownException
import cmc.goalmate.data.model.toDomain
import cmc.goalmate.domain.DataError
import cmc.goalmate.domain.DomainResult
import cmc.goalmate.domain.model.Login
import cmc.goalmate.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl
    @Inject
    constructor(private val authDataSource: AuthDataSource) : AuthRepository {
        override suspend fun login(idToken: String): DomainResult<Login, DataError.Network> =
            authDataSource.login(idToken).fold(
                onSuccess = { login ->
                    DomainResult.Success(login.toDomain())
                },
                onFailure = { error ->
                    DomainResult.Error(error.toDataError())
                },
            )
    }

fun Throwable.toDataError(): DataError.Network =
    when (this) {
        is HttpException -> {
            when (this.code) {
                401 -> DataError.Network.UNAUTHORIZED
                404 -> DataError.Network.NOT_FOUND
                409 -> DataError.Network.CONFLICT
                in 500..599 -> DataError.Network.SERVER_ERROR
                else -> DataError.Network.UNKNOWN
            }
        }
        is NetworkException -> DataError.Network.NO_INTERNET
        is UnknownException -> DataError.Network.UNKNOWN
        else -> DataError.Network.UNKNOWN
    }
