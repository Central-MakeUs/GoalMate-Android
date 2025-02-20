package cmc.goalmate.presentation.ui.progress.inprogress.model

import cmc.goalmate.presentation.ui.util.calculateProgress
import java.time.LocalDate

data class DailyProgressDetailUiModel(
    val selectedDate: LocalDate,
    val todos: List<TodoGoalUiModel>,
) {
    val completedTodayTodoCount: Int = todos.count { it.isCompleted }

    val totalTodayTodoCount: Int = todos.size

    val actualProgress: Float = calculateProgress(totalTodoCount = totalTodayTodoCount, totalCompletedCount = completedTodayTodoCount)

    fun canModifyTodo(comparedDate: LocalDate = LocalDate.now()): Boolean = selectedDate == comparedDate

    companion object {
        val DUMMY = DailyProgressDetailUiModel(
            selectedDate = LocalDate.of(2025, 2, 20),
            todos = TodoGoalUiModel.DUMMY,
        )
    }
}

data class TodoGoalUiModel(
    val id: Int,
    val content: String,
    val time: String,
    val isCompleted: Boolean,
    val tip: String? = null,
) {
    companion object {
        val DUMMY = listOf(
            TodoGoalUiModel(
                id = 0,
                content = "영어 단어 보카 30개 암기",
                time = "30분",
                isCompleted = false,
                tip = "영어 단어 보카 암기 할 때는 이렇게 하는 게 좋아요 블라블라",
            ),
            TodoGoalUiModel(id = 1, content = "영어 신문 읽기 1쪽", time = "30분", isCompleted = true),
        )

        val DUMMY2 = listOf(
            TodoGoalUiModel(id = 0, content = "팝송 부르기", time = "30분", isCompleted = false),
            TodoGoalUiModel(id = 1, content = "영어 동화책 읽기", time = "30분", isCompleted = true),
        )
    }
}
