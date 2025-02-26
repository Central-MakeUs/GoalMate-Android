package cmc.goalmate.presentation.ui.mypage

sealed interface MyPageAction {
    sealed interface MenuAction : MyPageAction {
        data object FAQ : MenuAction

        data object PrivacyPolicy : MenuAction

        data object TermsOfService : MenuAction

        data object Logout : MenuAction

        data object DeleteAccount : MenuAction

        data object EditNickName : MenuAction
    }

    data class SetNickName(val updated: String) : MyPageAction

    data class ConfirmNickName(val updated: String) : MyPageAction

    data class CheckDuplication(val updated: String) : MyPageAction
}

sealed interface MyPageEvent {
    data object ShowFAQ : MyPageEvent

    data object ShowTermsOfService : MyPageEvent

    data object ShowPrivacyPolicy : MyPageEvent

    data object SuccessLogout : MyPageEvent

    data object SuccessDeleteAccount : MyPageEvent

    data object EditNickName : MyPageEvent

    data object NeedLogin : MyPageEvent
}
