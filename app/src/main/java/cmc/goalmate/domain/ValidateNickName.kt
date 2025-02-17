package cmc.goalmate.domain

import javax.inject.Inject

class ValidateNickName
    @Inject
    constructor() {
        operator fun invoke(nickName: String): DomainResult<Unit, NickNameError> =
            if (nickName.length in 2..5) {
                DomainResult.Success(Unit)
            } else {
                DomainResult.Error(NickNameError.INVALID_LENGTH)
            }

        enum class NickNameError : Error {
            INVALID_LENGTH,
        }
    }
