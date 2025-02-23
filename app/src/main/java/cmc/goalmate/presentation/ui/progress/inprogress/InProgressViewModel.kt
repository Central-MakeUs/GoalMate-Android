package cmc.goalmate.presentation.ui.progress.inprogress

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import cmc.goalmate.app.navigation.Screen
import cmc.goalmate.domain.onFailure
import cmc.goalmate.domain.onSuccess
import cmc.goalmate.domain.repository.MenteeGoalRepository
import cmc.goalmate.presentation.ui.auth.asUiText
import cmc.goalmate.presentation.ui.progress.inprogress.mapper.toUi
import cmc.goalmate.presentation.ui.progress.inprogress.model.CalendarUiModel
import cmc.goalmate.presentation.ui.progress.inprogress.model.DailyProgressDetailUiModel
import cmc.goalmate.presentation.ui.progress.inprogress.model.DailyProgressUiModel
import cmc.goalmate.presentation.ui.progress.inprogress.model.GoalOverViewUiModel
import cmc.goalmate.presentation.ui.progress.inprogress.model.ProgressUiState
import cmc.goalmate.presentation.ui.progress.inprogress.model.UiState
import cmc.goalmate.presentation.ui.progress.inprogress.model.convertToDomain
import cmc.goalmate.presentation.ui.progress.inprogress.model.successData
import cmc.goalmate.presentation.ui.progress.inprogress.model.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class InProgressViewModel
    @Inject
    constructor(
        private val menteeGoalRepository: MenteeGoalRepository,
        savedStateHandle: SavedStateHandle,
    ) : ViewModel() {
        private val route = savedStateHandle.toRoute<Screen.InProgressGoal>()
        private val goalId = route.goalId
        private val commentRoomId = route.roomId

        private val _event = Channel<InProgressEvent>()
        val event = _event.receiveAsFlow()

        private val selectedDate = MutableStateFlow(LocalDate.now())
        private val weeklyProgressState = MutableStateFlow<UiState<CalendarUiModel>>(UiState.Loading)
        private val selectedDateTodoState =
            MutableStateFlow<UiState<DailyProgressDetailUiModel>>(UiState.Loading)
        private val goalInfoState = MutableStateFlow<UiState<GoalOverViewUiModel>>(UiState.Loading)

        val state: StateFlow<InProgressUiState> = combine(
            weeklyProgressState,
            selectedDateTodoState,
            goalInfoState,
            selectedDate,
        ) { weekly, daily, goal, date ->
            InProgressUiState(
                weeklyProgressState = weekly,
                selectedDailyState = daily,
                goalInfoState = goal,
                selectedDate = date,
            )
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            InProgressUiState.initialState,
        )

        init {
            loadInitialData()
            loadTodayTodos()
        }

        private fun loadInitialData() {
            viewModelScope.launch {
                val goalInfoResult = menteeGoalRepository.getGoalInfo(goalId)

                goalInfoResult.onSuccess { goalInfo ->
                    goalInfoState.update {
                        UiState.Success(goalInfo.toUi())
                    }
                    val goalMateCalendarResult = menteeGoalRepository.loadGoalMateCalendar(
                        menteeGoalId = goalId,
                        startDate = goalInfo.startDate,
                        endDate = goalInfo.endDate,
                        targetDate = LocalDate.now(),
                    )
                    goalMateCalendarResult.onSuccess { goalMateCalendar ->
                        weeklyProgressState.value = UiState.Success(goalMateCalendar.toUi())
                    }.onFailure { error ->
                        weeklyProgressState.value = UiState.Error(error.asUiText())
                    }
                }.onFailure { error ->
                    goalInfoState.update { UiState.Error(error.asUiText()) }
                }
            }
        }

        private fun loadTodayTodos() {
            viewModelScope.launch {
                menteeGoalRepository.getDailyTodos(goalId, LocalDate.now())
                    .onSuccess {
                        selectedDateTodoState.value = UiState.Success(it.toUi(LocalDate.now()))
                    }
                    .onFailure {
                        selectedDateTodoState.value = UiState.Error(it.asUiText())
                    }
            }
        }

        fun onAction(action: InProgressAction) {
            when (action) {
                is InProgressAction.CheckTodo -> updateTodoItem(action.todoId, action.currentState)
                is InProgressAction.SelectDate -> updateTodoList(action.selectedDate)
                is InProgressAction.UpdateNextMonth -> updateNextMonth()
                is InProgressAction.UpdatePreviousMonth -> updatePreviousMonth()
                InProgressAction.NavigateToComment -> {
                    sendEvent(
                        InProgressEvent.NavigateToComment(
                            commentRoomId,
                            startDate = goalInfoState.successData().startDate.toString(),
                        ),
                    )
                }

                InProgressAction.NavigateToGoalDetail -> sendEvent(
                    InProgressEvent.NavigateToGoalDetail(
                        goalId,
                    ),
                )

                is InProgressAction.SwipeLeft -> {
                }
            }
        }

        private fun sendEvent(event: InProgressEvent) {
            viewModelScope.launch {
                _event.send(event)
            }
        }

        private fun updateTodoItem(
            todoId: Int,
            currentState: Boolean,
        ) {
            val dailyProgress = selectedDateTodoState.successData()
            if (!dailyProgress.canModifyTodo()) {
                sendEvent(InProgressEvent.TodoModificationNotAllowed)
                return
            }
            val updatedState = !currentState
            updateTodosUi(dailyProgress = dailyProgress, todoId = todoId, updatedState = updatedState)

            viewModelScope.launch {
                menteeGoalRepository.updateTodoStatus(
                    menteeGoalId = goalId,
                    todoId = todoId,
                    updatedStatus = convertToDomain(updatedState),
                ).onFailure {
                    updateTodosUi(
                        dailyProgress = dailyProgress,
                        todoId = todoId,
                        updatedState = currentState,
                    )
                    // 에러 처리
                }
            }
        }

        private fun updateTodosUi(
            dailyProgress: DailyProgressDetailUiModel,
            todoId: Int,
            updatedState: Boolean,
        ) {
            val updatedTodos = dailyProgress.todos.map { todo ->
                if (todo.id == todoId) {
                    todo.copy(isCompleted = updatedState)
                } else {
                    todo
                }
            }
            selectedDateTodoState.value = UiState.Success(dailyProgress.copy(todos = updatedTodos))
        }

        private fun updateTodoList(newDate: DailyProgressUiModel) {
            if (newDate.status == ProgressUiState.NotInProgress) return

            selectedDate.value = newDate.actualDate
            selectedDateTodoState.value = UiState.Loading
            viewModelScope.launch {
                menteeGoalRepository.getDailyTodos(
                    menteeGoalId = goalId,
                    targetDate = newDate.actualDate,
                ).onSuccess { dailyTodos ->
                    selectedDateTodoState.value = UiState.Success(
                        dailyTodos.toUi(newDate.actualDate),
                    )
                }.onFailure {
                    selectedDateTodoState.value = UiState.Error(it.asUiText())
                }
            }
        }

        private fun updateNextMonth() {
            // TODO: 다음 주 데이터 요청
        }

        private fun updatePreviousMonth() {
            // TODO: 저번 주 데이터 요청
        }
    }
