package cmc.goalmate.presentation.ui.progress.inprogress.model

import java.time.LocalDate

data class CalendarUiModel(
    val todayWeekNumber: Int,
    val weeklyData: List<List<DailyProgressUiModel>>,
    val hasMorePreviousData: Boolean,
) {
    companion object {
        val DUMMY = CalendarUiModel(
            todayWeekNumber = 2,
            weeklyData = listOf(DailyProgressUiModel.DUMMY_LIST, DailyProgressUiModel.DUMMY_LIST_2),
            hasMorePreviousData = false,
        )
    }
}

data class DailyProgressUiModel(val actualDate: LocalDate, val status: ProgressUiState) {
    val displayedDate: Int = actualDate.dayOfMonth

    companion object {
        val DUMMY_LIST = listOf(
            DailyProgressUiModel(actualDate = LocalDate.of(2025, 2, 21), status = ProgressUiState.Completed(0.5f)),
            DailyProgressUiModel(actualDate = LocalDate.of(2025, 2, 22), status = ProgressUiState.Completed(0.8f)),
            DailyProgressUiModel(actualDate = LocalDate.of(2025, 2, 23), status = ProgressUiState.Completed(1f)),
            DailyProgressUiModel(actualDate = LocalDate.of(2025, 2, 24), status = ProgressUiState.InProgress),
            DailyProgressUiModel(actualDate = LocalDate.of(2025, 2, 25), status = ProgressUiState.NotStart),
            DailyProgressUiModel(actualDate = LocalDate.of(2025, 2, 26), status = ProgressUiState.NotStart),
            DailyProgressUiModel(actualDate = LocalDate.of(2025, 2, 27), status = ProgressUiState.NotStart),
        )
        val DUMMY_LIST_2 = listOf(
            DailyProgressUiModel(actualDate = LocalDate.of(2025, 2, 28), status = ProgressUiState.NotStart),
            DailyProgressUiModel(actualDate = LocalDate.of(2025, 3, 1), status = ProgressUiState.NotStart),
            DailyProgressUiModel(actualDate = LocalDate.of(2025, 3, 2), status = ProgressUiState.NotStart),
            DailyProgressUiModel(actualDate = LocalDate.of(2025, 3, 3), status = ProgressUiState.NotInProgress),
            DailyProgressUiModel(actualDate = LocalDate.of(2025, 3, 4), status = ProgressUiState.NotInProgress),
            DailyProgressUiModel(actualDate = LocalDate.of(2025, 3, 5), status = ProgressUiState.NotInProgress),
            DailyProgressUiModel(actualDate = LocalDate.of(2025, 3, 6), status = ProgressUiState.NotInProgress),
        )
    }
}

sealed interface ProgressUiState {
    data class Completed(val actualProgress: Float) : ProgressUiState {
        val displayProgress: Float = DailyProgressRateUiModel.fromActualProgress(actualProgress).displayProgress
    }

    data object InProgress : ProgressUiState // 오늘 날짜

    data object NotStart : ProgressUiState

    data object NotInProgress : ProgressUiState
}

enum class DailyProgressRateUiModel(val range: ClosedFloatingPointRange<Float>, val displayProgress: Float) {
    ZERO(0f..0f, 0f),
    ONE_TO_NINE(0.01f..0.09f, 32.4f),
    TEN_TO_NINETEEN(0.10f..0.19f, 36f),
    TWENTY_TO_TWENTY_NINE(0.20f..0.29f, 72f),
    THIRTY_TO_THIRTY_NINE(0.30f..0.39f, 108f),
    FORTY_TO_FORTY_NINE(0.40f..0.49f, 144f),
    FIFTY_TO_FIFTY_NINE(0.50f..0.59f, 180f),
    SIXTY_TO_SIXTY_NINE(0.60f..0.69f, 216f),
    SEVENTY_TO_SEVENTY_NINE(0.70f..0.79f, 252f),
    EIGHTY_TO_EIGHTY_NINE(0.80f..0.89f, 288f),
    NINETY_TO_NINETY_NINE(0.90f..0.99f, 324f),
    HUNDRED(1f..1f, 360f),
    ;

    companion object {
        fun fromActualProgress(actual: Float): DailyProgressRateUiModel = requireNotNull(entries.find { actual in it.range })
    }
}
