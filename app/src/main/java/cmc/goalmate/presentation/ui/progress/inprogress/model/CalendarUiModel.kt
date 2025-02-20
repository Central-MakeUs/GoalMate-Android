package cmc.goalmate.presentation.ui.progress.inprogress.model

import java.time.YearMonth

data class CalendarUiModel(
    val yearMonth: YearMonth,
    val hasNext: Boolean,
    val hasPrevious: Boolean,
    val progressByDate: List<DailyProgressUiModel>,
) {
    companion object {
        val DUMMY = CalendarUiModel(
            yearMonth = YearMonth.of(2025, 1),
            hasNext = true,
            hasPrevious = false,
            progressByDate = DailyProgressUiModel.DUMMY_LIST,
        )
    }
}

data class DailyProgressUiModel(val date: Int, val status: ProgressStatus) {
    companion object {
        val DUMMY_LIST = listOf(
            DailyProgressUiModel(date = 21, status = ProgressStatus.Completed(0.5f)),
            DailyProgressUiModel(date = 22, status = ProgressStatus.Completed(0.8f)),
            DailyProgressUiModel(date = 23, status = ProgressStatus.Completed(1f)),
            DailyProgressUiModel(date = 24, status = ProgressStatus.InProgress),
            DailyProgressUiModel(date = 25, status = ProgressStatus.NotStart),
            DailyProgressUiModel(date = 26, status = ProgressStatus.NotStart),
            DailyProgressUiModel(date = 27, status = ProgressStatus.NotInProgress),
        )
    }
}

sealed interface ProgressStatus {
    data class Completed(val actualProgress: Float) : ProgressStatus {
        val displayProgress: Float = DailyProgress.fromActualProgress(actualProgress).displayProgress
    }

    data object InProgress : ProgressStatus

    data object NotStart : ProgressStatus

    data object NotInProgress : ProgressStatus
}

enum class DailyProgress(val range: ClosedFloatingPointRange<Float>, val displayProgress: Float) {
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
        fun fromActualProgress(actual: Float): DailyProgress = requireNotNull(entries.find { actual in it.range })
    }
}
