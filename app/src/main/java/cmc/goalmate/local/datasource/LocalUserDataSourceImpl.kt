package cmc.goalmate.local.datasource

import cmc.goalmate.data.datasource.LocalUserDataSource
import cmc.goalmate.local.UserInfoDataStore
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalUserDataSourceImpl
    @Inject
    constructor(private val userInfoDataStore: UserInfoDataStore) : LocalUserDataSource {
        override fun getNickName(): Flow<String> = userInfoDataStore.userNickName

        override suspend fun saveNickName(nickName: String): Result<Unit> =
            runCatching {
                userInfoDataStore.saveNickName(nickName)
            }

        override suspend fun deleteNickName(): Result<Unit> =
            runCatching {
                userInfoDataStore.deleteNickName()
            }
    }
