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
            DailyProgressUiModel(date = 21, status = ProgressStatus.Completed(50)),
            DailyProgressUiModel(date = 22, status = ProgressStatus.Completed(80)),
            DailyProgressUiModel(date = 23, status = ProgressStatus.Completed(100)),
            DailyProgressUiModel(date = 24, status = ProgressStatus.InProgress),
            DailyProgressUiModel(date = 25, status = ProgressStatus.NotStart),
            DailyProgressUiModel(date = 26, status = ProgressStatus.NotStart),
            DailyProgressUiModel(date = 27, status = ProgressStatus.NotInProgress),
        )
    }
}

sealed interface ProgressStatus {
    data class Completed(val actualProgress: Int) : ProgressStatus {
        val displayProgress: Float = DailyProgress.fromActualProgress(actualProgress).displayProgress
    }

    data object InProgress : ProgressStatus

    data object NotStart : ProgressStatus

    data object NotInProgress : ProgressStatus
}

enum class DailyProgress(val range: IntRange, val displayProgress: Float) {
    ZERO(0..0, 0f),
    ONE_TO_NINE(1..9, 32.4f),
    TEN_TO_NINETEEN(10..19, 36f),
    TWENTY_TO_TWENTY_NINE(20..29, 72f),
    THIRTY_TO_THIRTY_NINE(30..39, 108f),
    FORTY_TO_FORTY_NINE(40..49, 144f),
    FIFTY_TO_FIFTY_NINE(50..59, 180f),
    SIXTY_TO_SIXTY_NINE(60..69, 216f),
    SEVENTY_TO_SEVENTY_NINE(70..79, 252f),
    EIGHTY_TO_EIGHTY_NINE(80..89, 288f),
    NINETY_TO_NINETY_NINE(90..99, 324f),
    HUNDRED(100..100, 360f),
    ;

    companion object {
        fun fromActualProgress(actual: Int): DailyProgress = requireNotNull(entries.find { actual in it.range })
    }
}
