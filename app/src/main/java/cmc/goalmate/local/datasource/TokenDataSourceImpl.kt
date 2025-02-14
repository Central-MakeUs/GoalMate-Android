package cmc.goalmate.local.datasource

import cmc.goalmate.data.datasource.TokenDataSource
import cmc.goalmate.data.model.TokenDto
import cmc.goalmate.local.TokenDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class TokenDataSourceImpl
    @Inject
    constructor(private val tokenDataStore: TokenDataStore) : TokenDataSource {
        override suspend fun getToken(): Flow<TokenDto> =
            combine(
                tokenDataStore.accessToken,
                tokenDataStore.refreshToken,
            ) { accessToken, refreshToken ->
                TokenDto(accessToken, refreshToken)
            }

        override suspend fun saveToken(token: TokenDto): Result<Unit> =
            runCatching {
                tokenDataStore.saveAccessToken(token.accessToken)
                tokenDataStore.saveRefreshToken(token.refreshToken)
            }

        override suspend fun deleteToken(): Result<Unit> =
            runCatching {
                tokenDataStore.deleteAccessToken()
                tokenDataStore.deleteRefreshToken()
            }
    }
