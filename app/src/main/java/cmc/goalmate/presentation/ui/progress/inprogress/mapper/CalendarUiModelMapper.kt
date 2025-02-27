package cmc.goalmate.presentation.ui.progress.inprogress.mapper

import cmc.goalmate.domain.model.DailyProgress
import cmc.goalmate.domain.model.GoalMateCalendar
import cmc.goalmate.domain.model.ProgressStatus
import cmc.goalmate.domain.model.Week
import cmc.goalmate.presentation.ui.progress.inprogress.model.CalendarUiModel
import cmc.goalmate.presentation.ui.progress.inprogress.model.DailyProgressUiModel
import cmc.goalmate.presentation.ui.progress.inprogress.model.ProgressUiState
import cmc.goalmate.presentation.ui.progress.inprogress.model.WeekUiModel
import cmc.goalmate.presentation.ui.util.calculateProgress

fun GoalMateCalendar.toUi(): CalendarUiModel =
    CalendarUiModel(
        todayWeekNumber = weekNumber,
        weeklyData = weeklyData.mapIndexed { index, week -> week.toUi(index) },
    )

fun Week.toUi(id: Int): WeekUiModel =
    WeekUiModel(
        id = id,
        dailyProgresses = dailyProgresses.map { it.toUi() },
        shouldLoadPrevious = shouldLoadPrevious,
    )

fun DailyProgress.toUi(): DailyProgressUiModel =
    when (this) {
        is DailyProgress.InvalidProgress -> {
            DailyProgressUiModel(
                actualDate = date,
                status = ProgressUiState.NotInProgress,
            )
        }

        is DailyProgress.ValidProgress -> {
            val progressUiState = when (getStatus()) {
                ProgressStatus.Completed -> ProgressUiState.Completed(
                    actualProgress = calculateProgress(
                        totalCompletedCount = completedDailyTodoCount,
                        totalTodoCount = dailyTodoCount,
                    ),
                )

                ProgressStatus.InProgress -> ProgressUiState.InProgress
                ProgressStatus.NotStarted -> ProgressUiState.NotStart
            }

            DailyProgressUiModel(
                actualDate = date,
                status = progressUiState,
            )
        }
    }
