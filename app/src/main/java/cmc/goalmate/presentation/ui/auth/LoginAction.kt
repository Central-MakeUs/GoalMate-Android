package cmc.goalmate.presentation.ui.auth

sealed interface LoginAction {
    data class KakaoLogin(val idToken: String?) : LoginAction

    data class SetNickName(val nickName: String) : LoginAction

    data object CheckDuplication : LoginAction

    data object CompleteNicknameSetup : LoginAction
}
