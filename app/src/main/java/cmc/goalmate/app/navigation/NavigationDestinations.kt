package cmc.goalmate.app.navigation

import androidx.navigation.NavController

data class InProgressGoalParams(val goalId: Int, val goalTitle: String, val roomId: Int)

fun NavController.navigateToInProgress(params: InProgressGoalParams) {
    navigate(Screen.InProgressGoal(goalId = params.goalId, goalTitle = params.goalTitle, roomId = params.roomId))
}

data class CompletedGoalParams(val goalId: Int, val roomId: Int)

fun NavController.navigateToCompleted(params: CompletedGoalParams) {
    navigate(Screen.CompletedGoal(goalId = params.goalId, roomId = params.roomId))
}

data class CommentDetailParams(val roomId: Int, val goalTitle: String, val startDate: String)

fun NavController.navigateToCommentDetail(params: CommentDetailParams) {
    navigate(Screen.CommentsDetail(roomId = params.roomId, goalTitle = params.goalTitle, startDate = params.startDate))
}
