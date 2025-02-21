package cmc.goalmate.domain.model

data class Goals(val goals: List<Goal>)

data class Goal(
    val id: Int,
    val title: String,
    val topic: String,
    val description: String,
    val period: Int,
    val dailyDuration: Int,
    val participantsLimit: Int,
    val currentParticipants: Int,
    val isClosingSoon: Boolean,
    val goalStatus: GoalStatus,
    val mentorName: String,
    val createdAt: String,
    val updatedAt: String,
    val mainImage: String?,
)

enum class GoalStatus {
    UP_COMING,
    OPEN,
    CLOSED,
}
