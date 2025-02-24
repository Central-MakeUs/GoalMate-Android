package cmc.goalmate.presentation.ui.mypage.model

import androidx.annotation.DrawableRes
import cmc.goalmate.R
import cmc.goalmate.domain.model.UserInfo
import cmc.goalmate.presentation.ui.mypage.MenuAction

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
            welcomeMessage = "회원가입하고 무료 목표 참여권 받으세요.\n",
            onGoingGoalCount = DEFAULT_COUNT,
            completedGoalCount = DEFAULT_COUNT,
            icon = R.drawable.icon_arrow_forward,
        )
    }
}

enum class MenuItemUiModel(
    val title: String,
    val menuAction: MenuAction,
) {
    FAQ("자주 묻는 질문", MenuAction.FAQ),
    PrivacyPolicy("개인 정보 처리 방침", MenuAction.PrivacyPolicy),
    TermsOfService("이용약관", MenuAction.TermsOfService),
    Logout("로그아웃", MenuAction.Logout),
    DeleteAccount("탈퇴하기", MenuAction.DeleteAccount),
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
