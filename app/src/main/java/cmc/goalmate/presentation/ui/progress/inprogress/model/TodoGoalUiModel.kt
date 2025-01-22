package cmc.goalmate.presentation.ui.progress.inprogress.model

data class TodoGoalUiModel(val id: Int, val content: String, val isCompleted: Boolean) {
    companion object {
        val DUMMY = listOf(
            TodoGoalUiModel(0, "영어 단어 보카 30개 암기", false),
            TodoGoalUiModel(1, "영어 신문 읽기 1쪽", true),
        )

        val DUMMY2 = listOf(
            TodoGoalUiModel(0, "팝송 부르기", false),
            TodoGoalUiModel(1, "영어 동화책 읽기", true),
        )
    }
}
