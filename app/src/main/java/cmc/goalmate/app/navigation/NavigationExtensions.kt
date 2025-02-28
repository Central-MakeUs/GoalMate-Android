package cmc.goalmate.app.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder
import cmc.goalmate.presentation.ui.common.WebScreenUrl

data class InProgressGoalParams(
    val menteeGoalId: Int,
    val goalId: Int,
    val goalTitle: String,
    val roomId: Int,
)

fun NavController.navigateToInProgress(
    params: InProgressGoalParams,
    navOptions: NavOptionsBuilder.() -> Unit = {},
) {
    navigate(
        Screen.InProgressGoal(
            menteeGoalId = params.menteeGoalId,
            goalTitle = params.goalTitle,
            roomId = params.roomId,
            goalId = params.goalId,
        ),
        navOptions,
    )
}

data class CompletedGoalParams(val menteeGoalId: Int, val goalId: Int, val roomId: Int)

fun NavController.navigateToCompleted(params: CompletedGoalParams) {
    navigate(
        Screen.CompletedGoal(
            menteeGoalId = params.menteeGoalId,
            roomId = params.roomId,
            goalId = params.goalId,
        ),
    )
}

data class CommentDetailParams(val roomId: Int, val goalTitle: String, val endDate: String)

fun NavController.navigateToCommentDetail(params: CommentDetailParams) {
    navigate(
        Screen.CommentsDetail(
            roomId = params.roomId,
            goalTitle = params.goalTitle,
            endDate = params.endDate,
        ),
    )
}

fun NavController.navigateToWebScreen(targetUrl: WebScreenUrl) {
    navigate(Screen.WebScreen(targetUrl))
}
