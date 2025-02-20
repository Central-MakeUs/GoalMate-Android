package cmc.goalmate.data.repository

import cmc.goalmate.data.datasource.MenteeGoalDataSource
import cmc.goalmate.data.mapper.toDataError
import cmc.goalmate.data.mapper.toDomain
import cmc.goalmate.data.model.toDomain
import cmc.goalmate.domain.DataError
import cmc.goalmate.domain.DomainResult
import cmc.goalmate.domain.model.DailyTodos
import cmc.goalmate.domain.model.MenteeGoalInfo
import cmc.goalmate.domain.model.MenteeGoals
import cmc.goalmate.domain.model.TodoStatus
import cmc.goalmate.domain.model.WeeklyProgress
import cmc.goalmate.domain.model.toInfo
import cmc.goalmate.domain.repository.MenteeGoalRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate
import javax.inject.Inject

class MenteeGoalRepositoryImpl
    @Inject
    constructor(private val menteeGoalDataSource: MenteeGoalDataSource) : MenteeGoalRepository {
        private val _goalInfo = MutableStateFlow<Map<Int, MenteeGoalInfo>>(emptyMap())
        val goalInfo: StateFlow<Map<Int, MenteeGoalInfo>> = _goalInfo.asStateFlow()

        override suspend fun getMenteeGoals(): DomainResult<MenteeGoals, DataError.Network> =
            menteeGoalDataSource.getGoals().fold(
                onSuccess = { goals ->
                    val goalsDomain = goals.toDomain()
                    val goalInfoMap = goalsDomain.goals.associateBy({ it.id }, { it.toInfo() })
                    _goalInfo.value = goalInfoMap
                    DomainResult.Success(goalsDomain)
                },
                onFailure = {
                    DomainResult.Error(it.toDataError())
                },
            )

        override suspend fun getGoalInfo(goalId: Int): DomainResult<MenteeGoalInfo, DataError> {
            val cachedGoal = goalInfo.value[goalId]

            return if (cachedGoal != null) {
                DomainResult.Success(cachedGoal)
            } else {
                DomainResult.Error(DataError.Local.NOT_FOUND)
            }
        }

        override suspend fun getWeeklyProgress(
            menteeGoalId: Int,
            targetDate: LocalDate,
        ): DomainResult<WeeklyProgress, DataError.Network> =
            menteeGoalDataSource.getWeeklyProgress(menteeGoalId, targetDate).fold(
                onSuccess = { weeklyProgress ->
                    DomainResult.Success(weeklyProgress.toDomain())
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
                    val result = DailyTodos(dailyTodoDto.todos.map { it.toDomain() })
                    updateGoalCache(dailyTodoDto.menteeGoal.toDomain().toInfo())
                    DomainResult.Success(result)
                },
                onFailure = {
                    DomainResult.Error(it.toDataError())
                },
            )

        private fun updateGoalCache(menteeGoal: MenteeGoalInfo) {
            _goalInfo.update { currentCache ->
                currentCache.toMutableMap().apply {
                    put(menteeGoal.id, menteeGoal)
                }
            }
        }

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
    }
