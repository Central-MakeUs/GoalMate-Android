package cmc.goalmate.data.repository

import cmc.goalmate.data.datasource.TokenDataSource
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
    }
