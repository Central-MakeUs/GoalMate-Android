package cmc.goalmate.presentation.ui.mypage.model

import androidx.annotation.DrawableRes
import cmc.goalmate.R
import cmc.goalmate.domain.model.UserInfo
import cmc.goalmate.presentation.ui.mypage.MyPageAction

data class MyPageUiModel(
    val nickName: String,
    val welcomeMessage: String,
    val onGoingGoalCount: Int,
    val completedGoalCount: Int,
    @DrawableRes val icon: Int,
) {
    companion object {
        const val DEFAULT_COUNT = 0

        val DEFAULT_INFO = MyPageUiModel(
            nickName = "로그인 회원가입",
            welcomeMessage = "회원가입하고 지금 바로 목표를 달성해보아요!\n",
            onGoingGoalCount = DEFAULT_COUNT,
            completedGoalCount = DEFAULT_COUNT,
            icon = R.drawable.icon_arrow_forward,
        )
    }
}

enum class MenuItemUiModel(
    val title: String,
    val menuAction: MyPageAction.MenuAction,
) {
    FAQ("자주 묻는 질문", MyPageAction.MenuAction.FAQ),
    PrivacyPolicy("개인 정보 처리 방침", MyPageAction.MenuAction.PrivacyPolicy),
    TermsOfService("이용약관", MyPageAction.MenuAction.TermsOfService),
    Logout("로그아웃", MyPageAction.MenuAction.Logout),
    DeleteAccount("탈퇴하기", MyPageAction.MenuAction.DeleteAccount),
    ;

    companion object {
        fun getMenuItems(isLoggedIn: Boolean): List<MenuItemUiModel> {
            val commonMenuItems = listOf(
                FAQ,
                PrivacyPolicy,
                TermsOfService,
            )

            val loggedInMenuItems = listOf(
                Logout,
                DeleteAccount,
            )
            return commonMenuItems + if (isLoggedIn) loggedInMenuItems else emptyList()
        }
    }
}

fun UserInfo.toUi(): MyPageUiModel =
    MyPageUiModel(
        nickName = nickName,
        welcomeMessage = "안녕하세요!\n" + "골메이트에 오신 것을 환영해요",
        onGoingGoalCount = inProgressGoalCount,
        completedGoalCount = completedGoalCount,
        icon = R.drawable.icon_edit,
    )
