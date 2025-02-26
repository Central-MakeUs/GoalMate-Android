package cmc.goalmate.data.datasource

import kotlinx.coroutines.flow.Flow

interface LocalUserDataSource {
    fun getNickName(): Flow<String>

    suspend fun saveNickName(nickName: String): Result<Unit>

    suspend fun deleteNickName(): Result<Unit>
}
