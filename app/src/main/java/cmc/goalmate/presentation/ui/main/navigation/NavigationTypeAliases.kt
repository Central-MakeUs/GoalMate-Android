package cmc.goalmate.presentation.ui.main.navigation

typealias NavigateToGoal = (goalId: Int) -> Unit

typealias NavigateToInProgress = (InProgressGoalParams) -> Unit

typealias NavigateToCompleted = (CompletedGoalParams) -> Unit

typealias NavigateToCommentDetail = (CommentDetailParams) -> Unit
