package cmc.goalmate.data.datasource

import cmc.goalmate.data.model.UserInfoDto
import cmc.goalmate.data.model.toData
import cmc.goalmate.data.util.getOrThrow
import cmc.goalmate.remote.service.MenteeService
import javax.inject.Inject

class RemoteUserDataSource
    @Inject
    constructor(private val menteeService: MenteeService) {
        suspend fun validateNickName(nickName: String): Result<Boolean> =
            runCatching {
                menteeService.isAvailableNickName(nickName)
                    .getOrThrow()
                    .isAvailable
            }

        suspend fun updateNickName(nickName: String): Result<Unit> =
            runCatching {
                menteeService.setNickName(nickName)
                    .getOrThrow()
                    .let { Unit }
            }

        suspend fun getUserInfo(): Result<UserInfoDto> =
            runCatching {
                menteeService.getUserInfo().getOrThrow().toData()
            }
    }
