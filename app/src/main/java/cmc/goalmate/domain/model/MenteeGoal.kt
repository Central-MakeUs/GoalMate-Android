package cmc.goalmate.domain.model

import java.time.LocalDate

data class MenteeGoals(val goals: List<MenteeGoal>)

data class MenteeGoal(
    val menteeGoalId: Int,
    val goalId: Int,
    val title: String,
    val topic: String,
    val mentorName: String,
    val mainImage: String?,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val todayTodoCount: Int,
    val todayCompletedCount: Int,
    val todayRemainingCount: Int,
    val totalTodoCount: Int,
    val totalCompletedCount: Int,
    val commentRoomId: Int,
    val menteeGoalStatus: MenteeGoalStatus,
)

sealed class MenteeGoalStatus {
    data object InProgress : MenteeGoalStatus()

    data class Completed(val finalComment: String) : MenteeGoalStatus()

    data object Canceled : MenteeGoalStatus()

    data object Unknown : MenteeGoalStatus()
}
