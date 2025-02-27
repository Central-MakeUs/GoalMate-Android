package cmc.goalmate.app.navigation

import cmc.goalmate.presentation.ui.common.WebScreenUrl
import cmc.goalmate.presentation.ui.detail.navigation.GoalSummary
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

        @Serializable
        data object Comments : Screen
    }

    @Serializable
    data object GoalDetail : Screen {
        @Serializable
        data class Detail(val goalId: Int) : Screen

        @Serializable
        data class PaymentCompleted(val newGoalId: Int, val newCommentRoomId: Int, val goalSummary: GoalSummary) : Screen
    }

    @Serializable
    data class CompletedGoal(val menteeGoalId: Int, val goalId: Int, val roomId: Int) : Screen

    @Serializable
    data class InProgressGoal(val menteeGoalId: Int, val goalId: Int, val goalTitle: String, val roomId: Int) : Screen

    @Serializable
    data class CommentsDetail(val roomId: Int, val goalTitle: String, val startDate: String) : Screen

    @Serializable
    data class WebScreen(val targetUrl: WebScreenUrl) : Screen
}
