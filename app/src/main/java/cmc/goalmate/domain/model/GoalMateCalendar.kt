package cmc.goalmate.domain.model

data class GoalMateCalendar(
    val weeklyData: List<List<DailyProgress>>,
    val weekNumber: Int,
    val shouldLoadPrevious: Boolean,
)
