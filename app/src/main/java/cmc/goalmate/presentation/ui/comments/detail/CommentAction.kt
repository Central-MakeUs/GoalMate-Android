package cmc.goalmate.presentation.ui.comments.detail

sealed interface CommentAction {
    data class WriteComment(val content: String) : CommentAction

    data class SendComment(val content: String) : CommentAction

    data class EditComment(val commentId: Int) : CommentAction

    data object CancelEdit : CommentAction

    data class DeleteComment(val commentId: Int) : CommentAction
}

sealed interface CommentEvent {
    data class StartEditComment(val currentContent: String) : CommentEvent

    data object CancelEdit : CommentEvent

    data object ShowSendingError : CommentEvent

    data object SuccessSending : CommentEvent
}
