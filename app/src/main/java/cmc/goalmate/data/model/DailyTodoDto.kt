package cmc.goalmate.data.model

data class DailyTodoDto(val menteeGoal: MenteeGoalDto, val todos: List<TodoDto>)

data class TodoDto(
    val id: Int,
    val todoDate: String,
    val estimatedMinutes: Int,
    val description: String,
    val mentorTip: String?,
    val todoStatus: String,
)
