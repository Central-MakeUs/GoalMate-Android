package cmc.goalmate.app.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface Screen {
    @Serializable
    data object Auth : Screen {
        @Serializable
        data object Login : Screen

        @Serializable
        data object NickNameSetting : Screen

        @Serializable
        data object Welcome : Screen
    }

    @Serializable
    data object Main : Screen {
        @Serializable
        data object Home : Screen

        @Serializable
        data object MyGoal : Screen

        @Serializable
        data object MyPage : Screen
    }

    @Serializable
    data class Detail(val goalId: Long) : Screen
}
