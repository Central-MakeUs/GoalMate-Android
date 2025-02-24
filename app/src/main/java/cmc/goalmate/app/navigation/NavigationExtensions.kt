package cmc.goalmate.app.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder
import cmc.goalmate.presentation.ui.common.WebScreenUrl

data class InProgressGoalParams(val goalId: Int, val goalTitle: String, val roomId: Int)

fun NavController.navigateToInProgress(
    params: InProgressGoalParams,
    navOptions: NavOptionsBuilder.() -> Unit = {},
) {
    navigate(Screen.InProgressGoal(goalId = params.goalId, goalTitle = params.goalTitle, roomId = params.roomId), navOptions)
}

data class CompletedGoalParams(val goalId: Int, val roomId: Int)

fun NavController.navigateToCompleted(params: CompletedGoalParams) {
    navigate(Screen.CompletedGoal(goalId = params.goalId, roomId = params.roomId))
}

data class CommentDetailParams(val roomId: Int, val goalTitle: String, val startDate: String)

fun NavController.navigateToCommentDetail(params: CommentDetailParams) {
    navigate(Screen.CommentsDetail(roomId = params.roomId, goalTitle = params.goalTitle, startDate = params.startDate))
}

fun NavController.navigateToWebScreen(targetUrl: WebScreenUrl) {
    navigate(Screen.WebScreen(targetUrl))
}
