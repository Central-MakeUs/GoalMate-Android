package cmc.goalmate.domain.model

data class GoalDetail(
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
    val weeklyObjectives: List<WeeklyObjective>,
    val midObjectives: List<MidObjective>,
    val thumbnailImages: List<String>,
    val contentImages: List<String>,
)

data class WeeklyObjective(
    val weekNumber: Int,
    val description: String,
)

data class MidObjective(
    val sequence: Int,
    val description: String,
)
