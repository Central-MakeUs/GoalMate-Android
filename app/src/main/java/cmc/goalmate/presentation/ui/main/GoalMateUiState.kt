package cmc.goalmate.presentation.ui.main

data class GoalMateUiState(
    val hasRemainingTodos: Boolean,
    val badgeCounts: Map<BottomNavItem, Int>,
) {
    val currentCommentBadgeCount: Int
        get() = badgeCounts.getOrDefault(BottomNavItem.COMMENTS, 0)

    companion object {
        const val DEFAULT_REMAINING_VALUE = false
        const val DEFAULT_NEW_COMMENT_COUNT = 0
        val INITIAL_STATE = GoalMateUiState(
            hasRemainingTodos = DEFAULT_REMAINING_VALUE,
            badgeCounts = BottomNavItem.entries.associateWith { DEFAULT_NEW_COMMENT_COUNT },
        )
    }
}
