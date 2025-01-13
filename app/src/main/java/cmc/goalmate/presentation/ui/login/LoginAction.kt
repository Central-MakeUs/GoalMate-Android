package cmc.goalmate.presentation.ui.login

sealed interface LoginAction {
    data object KakaoLogin : LoginAction

    data class SetNickName(val nickName: String) : LoginAction

    data object CheckDuplication : LoginAction

    data object CompleteNicknameSetup : LoginAction
}
