package cmc.goalmate.presentation.ui.util

import java.time.LocalDate
import java.time.temporal.ChronoUnit

fun calculateDaysBetween(
    endDate: LocalDate,
    startDate: LocalDate = LocalDate.now(),
): Int = ChronoUnit.DAYS.between(startDate, endDate).toInt().coerceAtLeast(0)

fun calculateProgress(
    totalCompletedCount: Int,
    totalTodoCount: Int,
): Float = if (totalTodoCount > 0) totalCompletedCount.toFloat() / totalTodoCount else 0f
