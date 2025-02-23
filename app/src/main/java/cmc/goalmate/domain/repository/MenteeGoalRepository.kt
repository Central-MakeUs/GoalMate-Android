package cmc.goalmate.domain.repository

import cmc.goalmate.domain.DataError
import cmc.goalmate.domain.DomainResult
import cmc.goalmate.domain.model.DailyTodos
import cmc.goalmate.domain.model.GoalMateCalendar
import cmc.goalmate.domain.model.MenteeGoalInfo
import cmc.goalmate.domain.model.MenteeGoals
import cmc.goalmate.domain.model.TodoStatus
import cmc.goalmate.domain.model.WeeklyProgress
import java.time.LocalDate

interface MenteeGoalRepository {
    suspend fun getMenteeGoals(): DomainResult<MenteeGoals, DataError.Network>

    suspend fun getGoalInfo(goalId: Int): DomainResult<MenteeGoalInfo, DataError>

    suspend fun getWeeklyProgress(
        menteeGoalId: Int,
        targetDate: LocalDate,
    ): DomainResult<WeeklyProgress, DataError.Network>

    suspend fun getDailyTodos(
        menteeGoalId: Int,
        targetDate: LocalDate,
    ): DomainResult<DailyTodos, DataError.Network>

    suspend fun updateTodoStatus(
        menteeGoalId: Int,
        todoId: Int,
        updatedStatus: TodoStatus,
    ): DomainResult<Unit, DataError.Network>

    suspend fun loadGoalMateCalendar(
        menteeGoalId: Int,
        startDate: LocalDate,
        endDate: LocalDate,
        targetDate: LocalDate,
    ): DomainResult<GoalMateCalendar, DataError.Network>
}
