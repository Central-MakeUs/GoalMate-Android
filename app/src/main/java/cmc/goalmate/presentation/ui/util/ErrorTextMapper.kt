package cmc.goalmate.presentation.ui.util

import cmc.goalmate.domain.DataError
import cmc.goalmate.domain.NickNameError

fun DataError.asUiText(): String =
    when (this) {
        DataError.Network.NO_INTERNET -> "No internet connection"
        DataError.Network.SERVER_ERROR -> "Server error"
        DataError.Network.NOT_FOUND -> "Not found"
        DataError.Network.UNAUTHORIZED -> "Unauthorized"
        DataError.Network.CONFLICT -> "Conflict"
        DataError.Network.UNKNOWN -> "Unknown error"
        DataError.Local.IO_ERROR -> "DataStore error"
        DataError.Local.NOT_FOUND -> "Caching error"
    }

fun NickNameError.asUiText(): String =
    when (this) {
        NickNameError.INVALID_LENGTH -> "2~5글자 닉네임을 입력해주세요."
        NickNameError.DUPLICATED -> "이미 있는 닉네임이에요 :("
        NickNameError.NETWORK_ERROR -> "네트워크 에러가 발생했어요. 다시 시도해주세요!"
    }
