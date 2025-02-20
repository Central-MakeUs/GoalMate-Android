package cmc.goalmate.domain.model

import java.time.LocalDate

data class DailyTodos(
    val todos: List<Todo>,
)

data class Todo(
    val id: Int,
    val todoDate: LocalDate,
    val estimatedMinutes: Int,
    val description: String,
    val mentorTip: String?,
    val todoStatus: TodoStatus,
)

enum class TodoStatus {
    IN_PROGRESS,
    COMPLETED,
}
