package cmc.goalmate.presentation.ui.progress.inprogress

import cmc.goalmate.presentation.ui.progress.inprogress.model.CalendarUiModel
import cmc.goalmate.presentation.ui.progress.inprogress.model.DailyProgressDetailUiModel
import cmc.goalmate.presentation.ui.progress.inprogress.model.GoalOverViewUiModel
import cmc.goalmate.presentation.ui.progress.inprogress.model.UiState
import java.time.LocalDate

data class InProgressUiState(
    val weeklyProgressState: UiState<CalendarUiModel>,
    val selectedDailyState: UiState<DailyProgressDetailUiModel>,
    val goalInfoState: UiState<GoalOverViewUiModel>,
    val selectedDate: LocalDate = LocalDate.now(),
) {
    companion object {
        val initialState = InProgressUiState(
            weeklyProgressState = UiState.Loading,
            selectedDailyState = UiState.Loading,
            goalInfoState = UiState.Loading,
        )
        val DUMMY = InProgressUiState(
            weeklyProgressState = UiState.Success(CalendarUiModel.DUMMY),
            selectedDailyState = UiState.Success(DailyProgressDetailUiModel.DUMMY),
            goalInfoState = UiState.Success(GoalOverViewUiModel.DUMMY),
        )
    }
}
