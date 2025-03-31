package cmc.goalmate.presentation.ui.util

import cmc.goalmate.domain.DataError
import cmc.goalmate.domain.NickNameError

fun DataError.asUiText(): String =
    when (this) {
        DataError.Network.NO_INTERNET -> "네트워크 상태를 확인해주세요!"
        DataError.Network.SERVER_ERROR -> "서버에 문제가 발생했습니다. 잠시 후 다시 시도해 주세요."
        DataError.Network.NOT_FOUND -> "요청한 정보를 찾을 수 없습니다. 다시 시도해 주세요."
        else -> "알 수 없는 오류가 발생했습니다. 잠시 후 다시 시도해 주세요"
    }

fun NickNameError.asUiText(): String =
    when (this) {
        NickNameError.INVALID_LENGTH -> "2~5글자 닉네임을 입력해주세요."
        NickNameError.DUPLICATED -> "이미 있는 닉네임이에요 :("
        NickNameError.NETWORK_ERROR -> "네트워크 에러가 발생했어요. 다시 시도해주세요!"
    }
