package cmc.goalmate.presentation.ui.progress.inprogress.model

import java.time.LocalDate

data class InProgressUiState(
    val weeklyProgressState: UiState<CalendarUiModel>,
    val selectedDailyState: UiState<DailyProgressDetailUiModel>,
    val goalInfoState: UiState<GoalOverViewUiModel>,
    val selectedDate: LocalDate = LocalDate.now(),
) {
    val isError: Boolean
        get() = (weeklyProgressState is UiState.Error) && (selectedDailyState is UiState.Error) && (goalInfoState is UiState.Error)

    companion object {
        val initialState =
            InProgressUiState(
                weeklyProgressState = UiState.Loading,
                selectedDailyState = UiState.Loading,
                goalInfoState = UiState.Loading,
            )
        val DUMMY =
            InProgressUiState(
                weeklyProgressState = UiState.Success(CalendarUiModel.DUMMY),
                selectedDailyState = UiState.Success(DailyProgressDetailUiModel.DUMMY),
                goalInfoState = UiState.Success(GoalOverViewUiModel.DUMMY),
            )
    }
}
