package cmc.goalmate.data.datasource

import cmc.goalmate.data.mapper.toData
import cmc.goalmate.data.model.DailyTodoDto
import cmc.goalmate.data.model.MenteeGoalsDto
import cmc.goalmate.data.model.TodoDto
import cmc.goalmate.data.model.WeeklyProgressDto
import cmc.goalmate.data.model.toData
import cmc.goalmate.data.util.getOrThrow
import cmc.goalmate.domain.model.TodoStatus
import cmc.goalmate.remote.dto.request.TodoRequest
import cmc.goalmate.remote.service.MenteeGoalService
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

class MenteeGoalDataSource
    @Inject
    constructor(private val menteeGoalService: MenteeGoalService) {
        suspend fun getGoals(): Result<MenteeGoalsDto> =
            runCatching {
                menteeGoalService.getMenteeGoals().getOrThrow().toData()
            }

        suspend fun getWeeklyProgress(
            menteeGoalId: Int,
            date: LocalDate,
        ): Result<WeeklyProgressDto> =
            runCatching {
                val formattedDate = date.format(formatter)
                menteeGoalService
                    .getWeeklyProgress(menteeGoalId, date = formattedDate)
                    .getOrThrow()
                    .toData()
            }

        suspend fun getDailyTodo(
            menteeGoalId: Int,
            date: LocalDate,
        ): Result<DailyTodoDto> =
            runCatching {
                val formattedDate = date.format(formatter)
                menteeGoalService.getDailyTodo(menteeGoalId, date = formattedDate)
                    .getOrThrow()
                    .toData()
            }

        suspend fun updateTodoStatus(
            menteeGoalId: Int,
            todoId: Int,
            updatedStatus: TodoStatus,
        ): Result<TodoDto> =
            runCatching {
                val request = TodoRequest(todoStatus = updatedStatus.toData())
                menteeGoalService.updateTodo(menteeGoalId, todoId, request).getOrThrow().toData()
            }
    }
