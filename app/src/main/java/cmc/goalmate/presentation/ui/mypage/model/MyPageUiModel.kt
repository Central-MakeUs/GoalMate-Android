package cmc.goalmate.presentation.ui.mypage.model

data class MyPageUiModel(
    val nickName: String,
    val onGoingGoalCount: Int,
    val completedGoalCount: Int,
) {
    companion object {
        const val DEFAULT_COUNT = 0
    }
}

data class MenuItemData(
    val title: String,
    val onClick: () -> Unit,
)
