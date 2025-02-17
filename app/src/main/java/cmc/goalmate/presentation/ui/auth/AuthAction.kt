package cmc.goalmate.presentation.ui.auth

sealed interface AuthAction {
    data class KakaoLogin(val idToken: String?) : AuthAction

    data class SetNickName(val nickName: String) : AuthAction

    data object CheckDuplication : AuthAction

    data object CompleteNicknameSetup : AuthAction
}
