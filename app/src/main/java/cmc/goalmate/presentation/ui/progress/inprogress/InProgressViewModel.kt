package cmc.goalmate.presentation.ui.progress.inprogress

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import cmc.goalmate.app.navigation.Screen
import cmc.goalmate.domain.onSuccess
import cmc.goalmate.domain.repository.MenteeGoalRepository
import cmc.goalmate.presentation.ui.progress.inprogress.mapper.toUi
import cmc.goalmate.presentation.ui.progress.inprogress.model.DailyProgressUiModel
import cmc.goalmate.presentation.ui.progress.inprogress.model.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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
        private val goalId = savedStateHandle.toRoute<Screen.InProgressGoal>().goalId

        private val _state = MutableStateFlow(InProgressUiState.initialState)
        val state: StateFlow<InProgressUiState>
            get() = _state

        init {
            loadGoalInfo(goalId)
        }

        private fun loadGoalInfo(id: Int) {
            // 목표 정보 로드
            viewModelScope.launch {
                menteeGoalRepository.getWeeklyProgress(id, LocalDate.now())
                    .onSuccess { weeklyData ->
                        _state.value = state.value.copy(weeklyProgressState = UiState.Success(weeklyData.toUi()))
                    }
            }
        }

        fun onAction(action: InProgressAction) {
            when (action) {
                is InProgressAction.CheckTodo -> updateTodoItem(action.todoId, action.updatedChecked)
                is InProgressAction.SelectDate -> updateTodoList(action.selectedDate)
                is InProgressAction.UpdateNextMonth -> updateNextMonth()
                is InProgressAction.UpdatePreviousMonth -> updatePreviousMonth()
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

        private fun updateNextMonth() {
            // TODO: 다음 주 데이터 요청
        }

        private fun updatePreviousMonth() {
            // TODO: 저번 주 데이터 요청
        }
    }
