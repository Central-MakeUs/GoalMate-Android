package cmc.goalmate.data.datasource

import cmc.goalmate.data.model.TokenDto
import kotlinx.coroutines.flow.Flow

interface TokenDataSource {
    suspend fun getToken(): Flow<TokenDto>

    suspend fun saveToken(token: TokenDto): Result<Unit>

    suspend fun deleteToken(): Result<Unit>
}
