package cmc.goalmate.presentation.ui.progress.inprogress

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cmc.goalmate.domain.repository.MenteeGoalRepository
import cmc.goalmate.presentation.ui.progress.inprogress.model.DailyProgressUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.YearMonth
import javax.inject.Inject

@HiltViewModel
class InProgressViewModel
    @Inject
    constructor(private val menteeGoalRepository: MenteeGoalRepository) : ViewModel() {
        private val _state = MutableStateFlow(InProgressUiState.initialState)
        val state: StateFlow<InProgressUiState>
            get() = _state

        private fun loadGoalInfo(id: Int) {
            // 목표 정보 로드
        }

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
            val updatedState = _state.value.updateTodoState(todoId, updated)
            _state.value = updatedState

            viewModelScope.launch {
                // 서버 통신 후, 성공이면 그대로, 실패시 rollback
            }
        }

        private fun updateTodoList(date: DailyProgressUiModel) {
            // 해당 날짜에 대한 투두 목록 불러오기
        }

        private fun updateNextMonth(currentYearMonth: YearMonth) {
            // TODO: 다음 주 데이터 요청
        }

        private fun updatePreviousMonth(currentYearMonth: YearMonth) {
            // TODO: 저번 주 데이터 요청
        }
    }
