package cmc.goalmate.presentation.ui.progress.inprogress

import android.util.Log
import androidx.lifecycle.ViewModel
import cmc.goalmate.presentation.ui.progress.inprogress.InProgressUiState.Companion.initialInProgressUiState
import cmc.goalmate.presentation.ui.progress.inprogress.model.DailyProgressUiModel
import cmc.goalmate.presentation.ui.progress.inprogress.model.TodoGoalUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.YearMonth
import javax.inject.Inject

@HiltViewModel
class InProgressViewModel
    @Inject
    constructor() : ViewModel() {
        private val _state = MutableStateFlow(initialInProgressUiState())
        val state: StateFlow<InProgressUiState>
            get() = _state

        // TODO :날짜별 투두 리스트 보관
        // TODO: 주차별 데이터 보관

        fun onAction(action: InProgressAction) {
            when (action) {
                is InProgressAction.CheckTodo -> updateTodoItem(action.todoId, action.updatedChecked)
                is InProgressAction.SelectDate -> updateTodoList(action.selectedDate)
                is InProgressAction.UpdateNextMonth -> updateNextMonth(action.currentYearMonth)
                is InProgressAction.UpdatePreviousMonth -> updatePreviousMonth(action.currentYearMonth)
                else -> return
            }
        }

        private fun updateTodoItem(
            todoId: Int,
            updated: Boolean,
        ) {
            val updatedTodos = state.value.todos.map { todo ->
                if (todo.id == todoId) {
                    todo.copy(isCompleted = updated)
                } else {
                    todo
                }
            }
            // TODO: 서버 연동
            _state.value = _state.value.copy(todos = updatedTodos)
        }

        private fun updateNextMonth(currentYearMonth: YearMonth) {
            // TODO: 다음 주 데이터 요청
        }

        private fun updatePreviousMonth(currentYearMonth: YearMonth) {
            // TODO: 저번 주 데이터 요청
        }

        private fun updateTodoList(date: DailyProgressUiModel) {
            _state.value = _state.value.copy(
                selectedDate = date,
                todos = TodoGoalUiModel.DUMMY2,
            )

            Log.d("yenny", "select Date : $date")
            Log.d("yenny", "updated State : ${_state.value.currentAchievementRate}")
        }
    }
