package cmc.goalmate.presentation.ui.progress.inprogress.model

import cmc.goalmate.domain.model.MenteeGoalInfo
import cmc.goalmate.presentation.ui.util.calculateProgress

data class GoalOverViewUiModel(
    val goalId: Int,
    val title: String,
    val mentor: String,
    val totalTodoCount: Int,
    val completedTodoCount: Int,
) {
    val totalProgress: Float
        get() = calculateProgress(
            totalTodoCount = totalTodoCount,
            totalCompletedCount = completedTodoCount,
        )

    companion object {
        val DUMMY = GoalOverViewUiModel(
            0,
            "다온과 함께하는 영어 완전 정복 목표 입니당",
            "다온",
            totalTodoCount = 20,
            completedTodoCount = 10,
        )
    }
}

fun MenteeGoalInfo.toUi(): GoalOverViewUiModel =
    GoalOverViewUiModel(
        goalId = id,
        title = title,
        mentor = mentorName,
        totalTodoCount = totalTodoCount,
        completedTodoCount = totalCompletedCount,
    )
