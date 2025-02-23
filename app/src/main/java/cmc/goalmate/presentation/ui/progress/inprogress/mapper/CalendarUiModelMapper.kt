package cmc.goalmate.presentation.ui.progress.inprogress.mapper

import cmc.goalmate.domain.model.DailyProgress
import cmc.goalmate.domain.model.GoalMateCalendar
import cmc.goalmate.domain.model.ProgressStatus
import cmc.goalmate.presentation.ui.progress.inprogress.model.CalendarUiModel
import cmc.goalmate.presentation.ui.progress.inprogress.model.DailyProgressUiModel
import cmc.goalmate.presentation.ui.progress.inprogress.model.ProgressUiState
import cmc.goalmate.presentation.ui.util.calculateProgress

// fun WeeklyProgress.toUi(): CalendarUiModel =
//    CalendarUiModel(
//        yearMonth = "${yearMonth.year}년 ${yearMonth.monthValue}월",
//        hasNext = hasNextWeek,
//        hasPrevious = hasLastWeek,
//        progressByDate = progressData.map { it.toUi() },
//    )

fun GoalMateCalendar.toUi(): CalendarUiModel =
    CalendarUiModel(
        todayWeekNumber = weekNumber,
        weeklyData = weeklyData.map { week ->
            week.map { it.toUi() }
        },
        hasMorePreviousData = shouldLoadPrevious,
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
