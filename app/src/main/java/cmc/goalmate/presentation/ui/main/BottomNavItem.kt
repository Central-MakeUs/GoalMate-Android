package cmc.goalmate.presentation.ui.main

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import cmc.goalmate.R
import cmc.goalmate.presentation.ui.main.navigation.Screen

enum class BottomNavItem(
    @StringRes val title: Int,
    @DrawableRes val icon: Int,
    val route: Screen,
) {
    HOME(title = R.string.home, icon = R.drawable.icon_home, Screen.Main.Home),

    MY_GOALS(title = R.string.my_goals, icon = R.drawable.icon_goal, Screen.Main.MyGoal),

    COMMENTS(title = R.string.comments, icon = R.drawable.icon_comment, Screen.Main.Comments),

    MY_PAGE(title = R.string.my_page, icon = R.drawable.icon_my, Screen.Main.MyPage),
    ;

    companion object {
        val bottomNavItemScreens = entries.map {
            it.route::class.qualifiedName
        }.toSet()
    }
}
