package cmc.goalmate.presentation.ui.detail

import cmc.goalmate.presentation.ui.detail.navigation.GoalSummary
import cmc.goalmate.presentation.ui.home.GoalState

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
    val weeklyGoal: List<Milestone>,
    val milestone: List<Milestone>,
    val detailImageUrl: String,
) {
    val isAvailable: Boolean
        get() = this.state == GoalState.AVAILABLE

    val remainingCount: Int
        get() = this.maxMembers - this.currentMembers

    companion object {
        val DUMMY = GoalDetailUiModel(
            title = "다온과 함께하는 영어 완전 정복 30일 목표",
            mentorName = "다온",
            imageUrls = listOf(),
            category = "영어",
            totalDates = "30일",
            startDate = "2025년 01월 01일",
            endDate = "2025년 01월 30일",
            time = "하루 평균 1시간",
            price = "10,000원",
            discount = "100%",
            totalPrice = "0원",
            currentMembers = 7,
            maxMembers = 23,
            state = GoalState.AVAILABLE,
            description = "“영어를 하고 싶었지만 어떤 방법으로 해야 할 지, 루틴을 세우지만 어떤 방법이 효율적일지 고민이 많지 않았나요?”",
            weeklyGoal = listOf(Milestone("1주차", "간단한 단어부터 시작하기"), Milestone("2주차", "기본 문장 읽기")),
            milestone = listOf(Milestone("1", "간단한 단어부터 시작하기"), Milestone("2", "기본 문장 읽기")),
            detailImageUrl = "",
        )
    }
}

fun GoalDetailUiModel.toSummary(): GoalSummary =
    GoalSummary(
        title = title,
        mentor = mentorName,
        price = price,
        totalPrice = totalPrice,
    )

data class Milestone(val label: String, val content: String)
