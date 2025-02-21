package cmc.goalmate.app.navigation

typealias NavigateToGoal = (goalId: Int) -> Unit

typealias NavigateToCommentDetail = (roomId: Int, goalTitle: String) -> Unit

typealias NavigateToInProgress = (goalId: Int, goalTitle: String) -> Unit
