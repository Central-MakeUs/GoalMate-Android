package cmc.goalmate.app.navigation

typealias NavigateToGoal = (goalId: Int) -> Unit

typealias NavigateToInProgress = (InProgressGoalParams) -> Unit

typealias NavigateToCompleted = (CompletedGoalParams) -> Unit

typealias NavigateToCommentDetail = (CommentDetailParams) -> Unit
