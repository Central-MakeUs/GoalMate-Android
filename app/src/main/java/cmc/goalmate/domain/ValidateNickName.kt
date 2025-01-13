package cmc.goalmate.domain

import javax.inject.Inject

class ValidateNickName
    @Inject
    constructor() {
        operator fun invoke(nickName: String) =
            if (nickName.length in 2..5) {
                ValidationResult(successful = true)
            } else {
                ValidationResult(successful = false, errorMessage = "2~5글자 닉네임을 입력해주세요.")
            }
    }

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: String? = null,
)
