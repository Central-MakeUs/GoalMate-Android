package cmc.goalmate.presentation.ui.comments.detail

import cmc.goalmate.presentation.ui.comments.detail.model.CommentUiModel
import cmc.goalmate.presentation.ui.comments.detail.model.MessageUiModel
import cmc.goalmate.presentation.ui.util.calculateDaysBetween
import java.time.LocalDate

fun List<CommentUiModel>.findMessageContentById(messageId: Int): String =
    this
        .flatMap { it.messages }
        .find { it.id == messageId }
        ?.content ?: error("해당 하는 코멘트를 찾을 수 없습니다! -> 코멘트 아이디 :$messageId")

fun List<CommentUiModel>.addMessage(
    newMessage: MessageUiModel,
    endDate: LocalDate,
): List<CommentUiModel> {
    val today = LocalDate.now()

    return when {
        isNotEmpty() && first().date == today -> {
            val updated = first().copy(messages = listOf(newMessage) + first().messages)
            listOf(updated) + drop(1)
        }

        else -> {
            listOf(
                CommentUiModel(
                    date = today,
                    daysFromStart = calculateDaysBetween(endDate = endDate),
                    messages = listOf(newMessage),
                ),
            ) + this
        }
    }
}

fun List<CommentUiModel>.replaceTempMessage(
    tmpId: Int,
    newId: Int,
): List<CommentUiModel> =
    this.map { comment ->
        comment.copy(
            messages =
                comment.messages.map { msg ->
                    if (msg.id == tmpId) msg.copy(id = newId) else msg
                },
        )
    }

fun List<CommentUiModel>.replaceContentMessage(
    targetId: Int,
    updatedComment: String,
): List<CommentUiModel> =
    this.map { comment ->
        val updatedMessages =
            comment.messages.map { msg ->
                if (msg.id == targetId) msg.copy(content = updatedComment) else msg
            }
        comment.copy(messages = updatedMessages)
    }

fun List<CommentUiModel>.removeMessage(targetId: Int): List<CommentUiModel> =
    this
        .map { comment ->
            comment.copy(messages = comment.messages.filterNot { it.id == targetId })
        }.filter { it.messages.isNotEmpty() }
