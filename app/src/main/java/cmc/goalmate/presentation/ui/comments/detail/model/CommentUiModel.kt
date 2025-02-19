package cmc.goalmate.presentation.ui.comments.detail.model

import cmc.goalmate.domain.model.Comment
import cmc.goalmate.domain.model.Comments
import cmc.goalmate.domain.model.Writer
import cmc.goalmate.presentation.ui.comments.detail.model.MessageUiModel.Companion.DUMMY_MENTEE
import cmc.goalmate.presentation.ui.comments.detail.model.MessageUiModel.Companion.DUMMY_MENTOR
import java.time.format.DateTimeFormatter

enum class SenderUiModel {
    MENTOR,
    MENTEE,
}

data class MessageUiModel(val id: Int, val content: String, val sender: SenderUiModel) {
    companion object {
        val DUMMY_MENTEE = MessageUiModel(
            id = 0,
            "선생님 영어 단어 암기 꿀팁좀 주세요ㅠㅠ선생님 영어 단어 암기 꿀팁좀 주세요ㅠㅠ선생님 영어 단어 암기 꿀팁좀 주세요ㅠㅠ",
            SenderUiModel.MENTEE,
        )
        val DUMMY_MENTOR = MessageUiModel(id = 1, "영어 단어 암기는 이렇게 하시면 됩니당", SenderUiModel.MENTOR)
    }
}

data class CommentUiModel(val date: String, val messages: List<MessageUiModel>) {
    companion object {
        val DUMMY =
            CommentUiModel(date = "2024년 11월 25일", messages = listOf(DUMMY_MENTEE, DUMMY_MENTOR))
    }
}

fun Comments.toUi(dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일")): List<CommentUiModel> {
    val groupedComments = mutableMapOf<String, MutableList<MessageUiModel>>()

    for (comment in this.comments) {
        val formattedDate = comment.commentedAt.toLocalDate().format(dateFormatter)
        groupedComments.getOrPut(formattedDate) { mutableListOf() }.add(comment.toUi())
    }

    return groupedComments.map { (date, messages) ->
        CommentUiModel(
            date = date,
            messages = messages,
        )
    }
}

fun Comment.toUi(): MessageUiModel =
    MessageUiModel(
        id = id,
        content = comment,
        sender = writerRole.toUi(),
    )

fun Writer.toUi(): SenderUiModel =
    when (this) {
        Writer.MENTEE -> SenderUiModel.MENTEE
        Writer.MENTOR -> SenderUiModel.MENTOR
    }
