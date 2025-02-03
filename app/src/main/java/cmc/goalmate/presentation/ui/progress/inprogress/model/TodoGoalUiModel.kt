package cmc.goalmate.presentation.ui.progress.inprogress.model

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
