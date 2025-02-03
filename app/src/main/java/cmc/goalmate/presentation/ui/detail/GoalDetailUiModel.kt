package cmc.goalmate.presentation.ui.detail

import cmc.goalmate.presentation.ui.detail.finish.navigation.GoalSummary
import cmc.goalmate.presentation.ui.home.GoalState

// + 이미 참여중일 경우?
data class GoalDetailUiModel(
    val title: String,
    val mentorName: String,
    val imageUrls: List<String>,
    val category: String,
    val totalDates: String,
    val startDate: String,
    val endDate: String,
    val time: String,
    val price: String,
    val discount: String,
    val totalPrice: String,
    val currentMembers: Int,
    val maxMembers: Int,
    val state: GoalState,
    val description: String,
    val weeklyGoal: List<String>,
    val milestone: List<String>,
    val detailImageUrl: String,
) {
    val isAvailable: Boolean
        get() = this.state == GoalState.AVAILABLE
}

fun GoalDetailUiModel.toSummary(): GoalSummary =
    GoalSummary(
        title = title,
        mentor = mentorName,
        price = price,
        totalPrice = totalPrice,
    )
