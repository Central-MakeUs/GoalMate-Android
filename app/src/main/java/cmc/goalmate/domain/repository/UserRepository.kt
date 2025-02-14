package cmc.goalmate.domain.repository

import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun isLogin(): Flow<Boolean>
}
