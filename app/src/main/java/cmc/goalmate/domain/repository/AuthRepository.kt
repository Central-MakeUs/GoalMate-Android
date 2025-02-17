package cmc.goalmate.domain.repository

import cmc.goalmate.domain.DataError
import cmc.goalmate.domain.DomainResult
import cmc.goalmate.domain.model.Login

interface AuthRepository {
    suspend fun login(idToken: String): DomainResult<Login, DataError.Network>
}
