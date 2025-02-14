package cmc.goalmate.presentation.ui.comments.detail.model

import cmc.goalmate.presentation.ui.comments.detail.model.MessageUiModel.Companion.DUMMY_MENTEE
import cmc.goalmate.presentation.ui.comments.detail.model.MessageUiModel.Companion.DUMMY_MENTOR

enum class Sender {
    MENTOR,
    MENTEE,
}

data class MessageUiModel(val content: String, val sender: Sender) {
    companion object {
        val DUMMY_MENTEE = MessageUiModel(
            "선생님 영어 단어 암기 꿀팁좀 주세요ㅠㅠ선생님 영어 단어 암기 꿀팁좀 주세요ㅠㅠ선생님 영어 단어 암기 꿀팁좀 주세요ㅠㅠ",
            Sender.MENTEE,
        )
        val DUMMY_MENTOR = MessageUiModel("영어 단어 암기는 이렇게 하시면 됩니당", Sender.MENTOR)
    }
}

data class CommentUiModel(val date: String, val messages: List<MessageUiModel>) {
    companion object {
        val DUMMY =
            CommentUiModel(date = "2024년 11월 25일", messages = listOf(DUMMY_MENTEE, DUMMY_MENTOR))
    }
}
