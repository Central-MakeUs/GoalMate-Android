package cmc.goalmate.presentation.ui.detail

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
)
