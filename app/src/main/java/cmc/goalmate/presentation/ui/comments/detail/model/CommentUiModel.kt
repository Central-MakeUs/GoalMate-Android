package cmc.goalmate.presentation.ui.comments.detail.model

import cmc.goalmate.domain.model.Comment
import cmc.goalmate.domain.model.Comments
import cmc.goalmate.domain.model.Writer
import cmc.goalmate.presentation.ui.comments.detail.model.MessageUiModel.Companion.DUMMY_MENTEE
import cmc.goalmate.presentation.ui.comments.detail.model.MessageUiModel.Companion.DUMMY_MENTOR
import cmc.goalmate.presentation.ui.util.calculateDaysBetween
import java.time.LocalDate
import java.time.format.DateTimeFormatter

enum class SenderUiModel {
    MENTOR,
    MENTEE,
    ADMIN,
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

data class CommentUiModel(val date: LocalDate, val daysFromStart: Int, val messages: List<MessageUiModel>) {
    val displayedDate: String
        get() = date.format(commentDateFormatter)

    companion object {
        val DUMMY =
            CommentUiModel(
                date = LocalDate.of(2024, 11, 25),
                daysFromStart = 3,
                messages = listOf(DUMMY_MENTEE, DUMMY_MENTOR),
            )
    }
}

val commentDateFormatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일")

fun Comments.toUi(goalEndDate: LocalDate): List<CommentUiModel> {
    val groupedComments = mutableMapOf<LocalDate, MutableList<MessageUiModel>>()

    for (comment in this.comments) {
        val formattedDate = comment.commentedAt.toLocalDate()
        groupedComments.getOrPut(formattedDate) { mutableListOf() }.add(comment.toUi())
    }

    return groupedComments.map { (date, messages) ->
        val remainingDays = calculateDaysBetween(
            endDate = goalEndDate,
            startDate = date,
        )
        CommentUiModel(
            date = date,
            daysFromStart = remainingDays,
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
        Writer.ADMIN -> SenderUiModel.ADMIN
    }
