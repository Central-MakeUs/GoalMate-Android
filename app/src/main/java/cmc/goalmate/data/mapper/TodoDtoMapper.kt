package cmc.goalmate.data.mapper

import cmc.goalmate.data.model.DailyTodoDto
import cmc.goalmate.data.model.TodoDto
import cmc.goalmate.domain.model.Todo
import cmc.goalmate.domain.model.TodoStatus
import cmc.goalmate.remote.dto.response.DailyTodoResponse
import cmc.goalmate.remote.dto.response.TodoResponse
import java.time.LocalDate

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

fun TodoStatus.toData(): String =
    when (this) {
        TodoStatus.COMPLETED -> "COMPLETED"
        TodoStatus.IN_PROGRESS -> "TODO"
    }
