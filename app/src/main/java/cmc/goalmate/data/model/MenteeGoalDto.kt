package cmc.goalmate.data.model

data class MenteeGoalsDto(val goals: List<MenteeGoalDto>)

data class MenteeGoalDto(
    val id: Int,
    val title: String,
    val topic: String,
    val mentorName: String,
    val mainImage: String?,
    val startDate: String,
    val endDate: String,
    val finalComment: String?,
    val todayTodoCount: Int,
    val todayCompletedCount: Int,
    val todayRemainingCount: Int,
    val totalTodoCount: Int,
    val totalCompletedCount: Int,
    val commentRoomId: Int,
    val menteeGoalStatus: String,
    val createdAt: String,
    val updatedAt: String,
)
