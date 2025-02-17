package cmc.goalmate.domain.repository

import cmc.goalmate.domain.DataError
import cmc.goalmate.domain.DomainResult
import cmc.goalmate.domain.model.Token
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun isLogin(): Flow<Boolean>

    suspend fun saveToken(token: Token): DomainResult<Unit, DataError.Local>

    suspend fun deleteToken(): DomainResult<Unit, DataError.Local>
}
