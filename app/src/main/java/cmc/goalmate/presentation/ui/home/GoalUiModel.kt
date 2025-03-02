package cmc.goalmate.presentation.ui.home

import cmc.goalmate.domain.model.Goal
import cmc.goalmate.domain.model.GoalStatus

data class GoalUiModel(
    val id: Int,
    val title: String,
    val imageUrl: String,
    val price: String = PLACEHOLDER_PRICE,
    val discount: String = PLACEHOLDER_PRICE,
    val totalPrice: String = PLACEHOLDER_PRICE,
    val isClosingSoon: Boolean,
    val currentMembers: Int,
    val maxMembers: Int,
    val remainingCount: Int,
    val state: GoalUiStatus,
) {
    companion object {
        private const val PLACEHOLDER_PRICE = "0"
    }
}

enum class GoalUiStatus {
    AVAILABLE,
    SOLD_OUT,
}

fun Goal.toUi(): GoalUiModel =
    GoalUiModel(
        id = this.id,
        title = this.title,
        imageUrl = this.mainImage ?: "",
        currentMembers = this.currentParticipants,
        maxMembers = this.participantsLimit,
        state = this.goalStatus.toUi(),
        remainingCount = this.participantsLimit - this.currentParticipants,
        isClosingSoon = if (this.goalStatus == GoalStatus.CLOSED) false else isClosingSoon,
    )

fun GoalStatus.toUi(): GoalUiStatus =
    when (this) {
        GoalStatus.CLOSED -> GoalUiStatus.SOLD_OUT
        else -> GoalUiStatus.AVAILABLE
    }

val dummyGoals = listOf(
    GoalUiModel(
        id = 0,
        title = "다온과 함께 하는 영어 뿌시기 목표",
        imageUrl = "",
        price = "100,000원",
        discount = "20,000원",
        totalPrice = "80,000원",
        currentMembers = 5,
        maxMembers = 20,
        state = GoalUiStatus.AVAILABLE,
        remainingCount = 15,
        isClosingSoon = true,
    ),
    GoalUiModel(
        id = 1,
        title = "스더와 함께 하는 디자인 공부",
        imageUrl = "",
        price = "200,000원",
        discount = "50,000원",
        totalPrice = "150,000원",
        currentMembers = 10,
        maxMembers = 30,
        state = GoalUiStatus.AVAILABLE,
        isClosingSoon = false,
        remainingCount = 20,
    ),
    GoalUiModel(
        id = 2,
        title = "새로운 목표 3",
        imageUrl = "",
        price = "50,000원",
        discount = "10,000원",
        totalPrice = "40,000원",
        currentMembers = 8,
        maxMembers = 15,
        remainingCount = 20,
        state = GoalUiStatus.AVAILABLE,
        isClosingSoon = false,
    ),
    GoalUiModel(
        id = 3,
        title = "새로운 목표 4",
        imageUrl = "",
        price = "300,000원",
        discount = "70,000원",
        totalPrice = "230,000원",
        currentMembers = 15,
        maxMembers = 50,
        remainingCount = 20,
        state = GoalUiStatus.SOLD_OUT,
        isClosingSoon = false,
    ),
)
