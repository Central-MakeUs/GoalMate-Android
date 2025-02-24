package cmc.goalmate.domain.model

data class GoalMateCalendar(
    val weeklyData: List<Week>,
    val weekNumber: Int,
)

data class Week(
    val dailyProgresses: List<DailyProgress>,
    val shouldLoadPrevious: Boolean,
)
