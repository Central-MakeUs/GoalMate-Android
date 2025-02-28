package cmc.goalmate.data.repository

import cmc.goalmate.data.datasource.MenteeGoalDataSource
import cmc.goalmate.data.mapper.toDataError
import cmc.goalmate.data.mapper.toDomain
import cmc.goalmate.data.model.toWeekDomain
import cmc.goalmate.domain.DataError
import cmc.goalmate.domain.DomainResult
import cmc.goalmate.domain.generateWeeklyCalendar
import cmc.goalmate.domain.model.DailyTodos
import cmc.goalmate.domain.model.GoalMateCalendar
import cmc.goalmate.domain.model.MenteeGoalInfo
import cmc.goalmate.domain.model.MenteeGoals
import cmc.goalmate.domain.model.TodoStatus
import cmc.goalmate.domain.model.Week
import cmc.goalmate.domain.model.toInfo
import cmc.goalmate.domain.repository.MenteeGoalRepository
import cmc.goalmate.domain.updateProgressForWeeks
import java.time.LocalDate
import javax.inject.Inject

class MenteeGoalRepositoryImpl
    @Inject
    constructor(private val menteeGoalDataSource: MenteeGoalDataSource) : MenteeGoalRepository {
        private val goalInfo: MutableMap<Int, MenteeGoalInfo> = mutableMapOf<Int, MenteeGoalInfo>()

        override suspend fun getMenteeGoals(): DomainResult<MenteeGoals, DataError.Network> =
            menteeGoalDataSource.getGoals().fold(
                onSuccess = { goalsDto ->
                    val goals = goalsDto.toDomain()
                    val updatedGoals = goals.goals.associateBy({ it.menteeGoalId }, { it.toInfo() })
                    goalInfo.putAll(updatedGoals)
                    DomainResult.Success(goals)
                },
                onFailure = {
                    DomainResult.Error(it.toDataError())
                },
            )

        override suspend fun getGoalInfo(goalId: Int): DomainResult<MenteeGoalInfo, DataError> {
            val cachedGoal = goalInfo[goalId]
            if (cachedGoal != null) return DomainResult.Success(cachedGoal)

            return menteeGoalDataSource.getDailyTodo(goalId, LocalDate.now()).fold(
                onSuccess = {
                    DomainResult.Success(it.menteeGoal.toDomain().toInfo())
                },
                onFailure = {
                    DomainResult.Error(it.toDataError())
                },
            )
        }

        override suspend fun getWeeklyProgress(
            menteeGoalId: Int,
            targetDate: LocalDate,
        ): DomainResult<Week, DataError.Network> =
            menteeGoalDataSource.getWeeklyProgress(menteeGoalId, targetDate).fold(
                onSuccess = { weeklyProgress ->
                    DomainResult.Success(weeklyProgress.toWeekDomain())
                },
                onFailure = {
                    DomainResult.Error(it.toDataError())
                },
            )

        override suspend fun getDailyTodos(
            menteeGoalId: Int,
            targetDate: LocalDate,
        ): DomainResult<DailyTodos, DataError.Network> =
            menteeGoalDataSource.getDailyTodo(menteeGoalId, targetDate).fold(
                onSuccess = { dailyTodoDto ->
                    goalInfo[dailyTodoDto.menteeGoal.menteeGoalId] =
                        dailyTodoDto.menteeGoal.toDomain().toInfo()
                    val result = DailyTodos(dailyTodoDto.todos.map { it.toDomain() })
                    DomainResult.Success(result)
                },
                onFailure = {
                    DomainResult.Error(it.toDataError())
                },
            )

        override suspend fun updateTodoStatus(
            menteeGoalId: Int,
            todoId: Int,
            updatedStatus: TodoStatus,
        ): DomainResult<Unit, DataError.Network> =
            menteeGoalDataSource.updateTodoStatus(menteeGoalId, todoId, updatedStatus).fold(
                onSuccess = {
                    DomainResult.Success(Unit)
                },
                onFailure = {
                    DomainResult.Error(it.toDataError())
                },
            )

        override suspend fun loadInitialGoalMateCalendar(
            menteeGoalId: Int,
            startDate: LocalDate,
            endDate: LocalDate,
            targetDate: LocalDate,
        ): DomainResult<GoalMateCalendar, DataError.Network> {
            val weeklyProgressDto = menteeGoalDataSource.getWeeklyProgress(menteeGoalId, targetDate)
                .getOrElse { return DomainResult.Error(it.toDataError()) }
            val initialCalendar =
                generateWeeklyCalendar(startDate = startDate, endDate = endDate, target = targetDate)

            val updatedWeekData = updateProgressForWeeks(
                weeks = initialCalendar.weeklyData,
                updatedWeekProgressDtos = weeklyProgressDto.progressList,
            )
            val updatedCalendar = initialCalendar.copy(weeklyData = updatedWeekData)
            if (!weeklyProgressDto.hasLastWeek) { // targetDate가 첫 주 일 경우
                return DomainResult.Success(updatedCalendar)
            }
            // 이전 주차로 한 번 더 매핑
            val newTargetDate = targetDate.minusWeeks(1)
            val newWeeklyProgressDto =
                menteeGoalDataSource.getWeeklyProgress(menteeGoalId, newTargetDate)
                    .getOrElse { return DomainResult.Error(it.toDataError()) }
            val newUpdatedWeekData = updateProgressForWeeks(
                weeks = updatedCalendar.weeklyData,
                updatedWeekProgressDtos = newWeeklyProgressDto.progressList,
            )
            return DomainResult.Success(
                updatedCalendar.copy(
                    weeklyData = newUpdatedWeekData,
                ),
            )
        }

        override suspend fun hasRemainingTodosToday(): DomainResult<Boolean, DataError.Network> =
            menteeGoalDataSource.checkForInCompletedTodos()
                .fold(
                    onSuccess = {
                        DomainResult.Success(it)
                    },
                    onFailure = {
                        DomainResult.Error(it.toDataError())
                    },
                )
    }
