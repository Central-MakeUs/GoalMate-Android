package cmc.goalmate.data.model

import cmc.goalmate.data.mapper.toData
import cmc.goalmate.domain.model.Todo
import cmc.goalmate.domain.model.TodoStatus
import cmc.goalmate.remote.dto.response.DailyTodoResponse
import cmc.goalmate.remote.dto.response.TodoResponse
import java.time.LocalDate

data class DailyTodoDto(val menteeGoal: MenteeGoalDto, val todos: List<TodoDto>)

data class TodoDto(
    val id: Int,
    val todoDate: String,
    val estimatedMinutes: Int,
    val description: String,
    val mentorTip: String?,
    val todoStatus: String,
)

fun TodoResponse.toData(): TodoDto =
    TodoDto(
        id = id,
        todoDate = todoDate,
        estimatedMinutes = estimatedMinutes,
        description = description,
        mentorTip = mentorTip,
        todoStatus = todoStatus,
    )

fun DailyTodoResponse.toData(): DailyTodoDto =
    DailyTodoDto(
        menteeGoal = menteeGoal.toData(),
        todos = todos.map { it.toData() },
    )

fun TodoDto.convertTodoStatus(): TodoStatus =
    when (this.todoStatus) {
        "COMPLETED" -> TodoStatus.COMPLETED
        "TODO" -> TodoStatus.IN_PROGRESS
        else -> error("유효하지 않은 투두 상태 값: ${this.todoStatus} ")
    }

fun TodoDto.toDomain(): Todo =
    Todo(
        id = id,
        todoDate = LocalDate.parse(todoDate),
        estimatedMinutes = estimatedMinutes,
        description = description,
        mentorTip = mentorTip,
        todoStatus = convertTodoStatus(),
    )
