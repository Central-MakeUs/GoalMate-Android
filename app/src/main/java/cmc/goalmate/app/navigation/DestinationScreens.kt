package cmc.goalmate.app.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface Screen {
    @Serializable
    data object Auth : Screen {
        @Serializable
        data object Login : Screen
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
}
