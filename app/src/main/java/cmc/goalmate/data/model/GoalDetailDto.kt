package cmc.goalmate.data.model

data class GoalDetailDto(
    val id: Int,
    val title: String,
    val topic: String,
    val description: String,
    val period: Int,
    val dailyDuration: Int,
    val participantsLimit: Int,
    val currentParticipants: Int,
    val isClosingSoon: Boolean,
    val goalStatus: String,
    val mentorName: String,
    val createdAt: String,
    val updatedAt: String,
    val weeklyObjectives: List<WeeklyObjectiveDto>,
    val midObjectives: List<MidObjectiveDto>,
    val thumbnailImages: List<ImageDto>,
    val contentImages: List<ImageDto>,
    val isParticipated: Boolean,
)

data class WeeklyObjectiveDto(
    val weekNumber: Int,
    val description: String,
)

data class MidObjectiveDto(
    val sequence: Int,
    val description: String,
)

data class ImageDto(
    val sequence: Int,
    val imageUrl: String,
)
