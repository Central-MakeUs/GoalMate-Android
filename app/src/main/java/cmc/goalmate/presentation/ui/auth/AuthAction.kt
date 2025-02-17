package cmc.goalmate.presentation.ui.auth

sealed interface AuthAction {
    data class KakaoLogin(val idToken: String?) : AuthAction

    data object AgreeTermsOfService : AuthAction

    data class SetNickName(val nickName: String) : AuthAction

    data object CheckDuplication : AuthAction

    data object CompleteNicknameSetup : AuthAction
}

sealed interface AuthEvent {
    data object GetAgreeWithTerms : AuthEvent

    data object NavigateToHome : AuthEvent

    data object NavigateToNickNameSetting : AuthEvent

    data object NavigateToCompleted : AuthEvent
}
