package cmc.goalmate.presentation.ui.detail

import cmc.goalmate.presentation.ui.detail.navigation.GoalSummary

sealed interface GoalDetailAction {
    data object InitiateGoal : GoalDetailAction

    data object ConfirmGoalStart : GoalDetailAction
}

sealed interface GoalDetailEvent {
    data class NavigateToGoalStart(val newGoalId: Int, val goalSummary: GoalSummary) : GoalDetailEvent

    data object NavigateToLogin : GoalDetailEvent

    data object ShowGoalStartConfirmation : GoalDetailEvent
}
