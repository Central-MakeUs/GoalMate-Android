package cmc.goalmate.presentation.ui.mygoals

import cmc.goalmate.domain.model.MenteeGoal
import cmc.goalmate.domain.model.MenteeGoalStatus
import cmc.goalmate.domain.model.MenteeGoals
import cmc.goalmate.presentation.ui.util.calculateDaysFromStart
import cmc.goalmate.presentation.ui.util.calculateProgress
import java.time.format.DateTimeFormatter

data class MyGoalUiModel(
    val goalId: Int,
    val title: String,
    val thumbnailUrl: String,
    val mentorName: String,
    val startDate: String,
    val endDate: String,
    val daysFromStart: Int,
    val goalProgress: Float,
    val goalState: MyGoalUiState,
    val remainGoals: Int = 0,
) {
    companion object {
        val DUMMY = MyGoalUiModel(
            goalId = 0,
            title = "다온과 함께하는 영어 완전 정복",
            thumbnailUrl = "",
            mentorName = "다온",
            startDate = "2025년 01월 01일 부터",
            endDate = "2025년 01월 30일까지",
            daysFromStart = 2,
            goalProgress = 0.2f,
            goalState = MyGoalUiState.IN_PROGRESS,
        )
        val DUMMY2 = MyGoalUiModel(
            goalId = 0,
            title = "마루와 함께하는 백앤드 서버 찐천재 목표",
            thumbnailUrl = "",
            mentorName = "마루",
            startDate = "2025년 01월 01일 부터",
            endDate = "2025년 01월 30일까지",
            daysFromStart = 2,
            goalProgress = 0.2f,
            goalState = MyGoalUiState.COMPLETED,
        )
    }
}

enum class MyGoalUiState(
    val label: String,
    private val dateFormat: String,
) {
    IN_PROGRESS(label = "진행중", dateFormat = "D-%s"),
    COMPLETED(label = "진행완료", dateFormat = "done"),
    ;

    fun getDateTag(daysUntilDeadline: Int): String = dateFormat.format(daysUntilDeadline)
}

fun MenteeGoals.toUi(): List<MyGoalUiModel> = this.goals.map { it.toUi() }

fun MenteeGoal.toUi(formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일")): MyGoalUiModel =
    MyGoalUiModel(
        goalId = id,
        title = title,
        thumbnailUrl = mainImage ?: "",
        mentorName = mentorName,
        startDate = "${startDate.format(formatter)} 부터",
        endDate = "${endDate.format(formatter)} 까지",
        daysFromStart = calculateDaysFromStart(endDate),
        goalProgress = calculateProgress(totalCompletedCount, totalTodoCount),
        goalState = menteeGoalStatus.toUi(),
        remainGoals = todayRemainingCount,
    )

fun MenteeGoalStatus.toUi(): MyGoalUiState =
    when (this) {
        MenteeGoalStatus.InProgress -> MyGoalUiState.IN_PROGRESS
        else -> MyGoalUiState.COMPLETED
    }
