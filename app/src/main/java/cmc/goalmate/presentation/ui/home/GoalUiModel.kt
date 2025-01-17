package cmc.goalmate.presentation.ui.home

data class GoalUiModel(
    val id: Long,
    val title: String,
    val imageUrl: String,
    val price: String,
    val discount: String,
    val totalPrice: String,
    val currentMembers: Int,
    val maxMembers: Int,
    val state: GoalState,
)

enum class GoalState {
    AVAILABLE,
    SOLD_OUT,
}

val dummyGoals = listOf(
    GoalUiModel(
        id = 0L,
        title = "새로운 목표 1",
        imageUrl = "",
        price = "100,000원",
        discount = "20,000원",
        totalPrice = "80,000원",
        currentMembers = 5,
        maxMembers = 20,
        state = GoalState.AVAILABLE,
    ),
    GoalUiModel(
        id = 1L,
        title = "새로운 목표 2",
        imageUrl = "",
        price = "200,000원",
        discount = "50,000원",
        totalPrice = "150,000원",
        currentMembers = 10,
        maxMembers = 30,
        state = GoalState.AVAILABLE,
    ),
    GoalUiModel(
        id = 2L,
        title = "새로운 목표 3",
        imageUrl = "",
        price = "50,000원",
        discount = "10,000원",
        totalPrice = "40,000원",
        currentMembers = 8,
        maxMembers = 15,
        state = GoalState.AVAILABLE,
    ),
    GoalUiModel(
        id = 3L,
        title = "새로운 목표 4",
        imageUrl = "",
        price = "300,000원",
        discount = "70,000원",
        totalPrice = "230,000원",
        currentMembers = 15,
        maxMembers = 50,
        state = GoalState.SOLD_OUT,
    ),
)
