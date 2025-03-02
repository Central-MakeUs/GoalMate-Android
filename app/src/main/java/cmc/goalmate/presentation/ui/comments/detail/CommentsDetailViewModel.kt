package cmc.goalmate.presentation.ui.comments.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import cmc.goalmate.app.navigation.Screen
import cmc.goalmate.domain.onFailure
import cmc.goalmate.domain.onSuccess
import cmc.goalmate.domain.repository.CommentRepository
import cmc.goalmate.presentation.ui.comments.detail.model.CommentTextState
import cmc.goalmate.presentation.ui.comments.detail.model.CommentsUiState
import cmc.goalmate.presentation.ui.comments.detail.model.MessageUiModel
import cmc.goalmate.presentation.ui.comments.detail.model.SenderUiModel
import cmc.goalmate.presentation.ui.comments.detail.model.success
import cmc.goalmate.presentation.ui.comments.detail.model.successData
import cmc.goalmate.presentation.ui.comments.detail.model.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class CommentsDetailViewModel
    @Inject
    constructor(
        savedStateHandle: SavedStateHandle,
        private val commentRepository: CommentRepository,
    ) : ViewModel() {
        private val route = savedStateHandle.toRoute<Screen.CommentsDetail>()
        private val roomId = route.roomId
        private val endDate = LocalDate.parse(route.endDate)

        private val _state = MutableStateFlow<CommentsUiState>(CommentsUiState.Loading)
        val state: StateFlow<CommentsUiState> = _state
            .onStart {
                loadComments(roomId)
            }.stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000L),
                CommentsUiState.Loading,
            )

        private val _event = Channel<CommentEvent>()
        val event = _event.receiveAsFlow()

        private var editingCommentId: Int? = null

        var commentContent by mutableStateOf("")
            private set

        private fun loadComments(id: Int) {
            viewModelScope.launch {
                commentRepository.getComments(id)
                    .onSuccess { comments ->
                        val commentsUiModel = comments.toUi(goalEndDate = endDate)
                        _state.value =
                            CommentsUiState.Success(
                                comments = commentsUiModel,
                                commentTextState = CommentTextState.Empty,
                            )
                    }
                    .onFailure {
                        _state.value = CommentsUiState.Error
                    }
            }
        }

        fun onAction(action: CommentAction) {
            when (action) {
                is CommentAction.WriteComment -> {
                    writeComment(action.content)
                }

                is CommentAction.DeleteComment -> {
                    deleteComment(action.commentId)
                }

                is CommentAction.EditComment -> {
                    editingCommentId = action.commentId
                    val content = state.successData().comments.findMessageContentById(action.commentId)
                    setFilledTextState(content, textState = CommentTextState.UnChanged)
                    sendEvent(CommentEvent.StartEditComment(currentContent = content))
                }

                CommentAction.CancelEdit -> {
                    editingCommentId = null
                    setEmptyTextState()
                    sendEvent(CommentEvent.CancelEdit)
                }

                is CommentAction.SendComment -> {
                    if (editingCommentId != null) {
                        editComment(targetId = editingCommentId!!, comment = action.content)
                    } else {
                        uploadNewComment(action.content)
                    }
                }

                CommentAction.InValidRequest -> sendEvent(CommentEvent.ShowSendingError)
            }
        }

        private fun sendEvent(event: CommentEvent) {
            viewModelScope.launch {
                _event.send(event)
            }
        }

        private fun writeComment(content: String) {
            commentContent = content.take(MAXIMUM_MESSAGE_LENGTH)
            if (content.isNotEmpty() && state.successData().commentTextState != CommentTextState.Filled) {
                _state.update { state ->
                    state.success().copy(commentTextState = CommentTextState.Filled)
                }
            }
        }

        private fun deleteComment(commentId: Int) {
            val beforeData = state.successData().comments

            _state.update { state ->
                val updated = state.success().comments.removeMessage(commentId)
                state.success().copy(comments = updated)
            }

            viewModelScope.launch {
                commentRepository.deleteComment(commentId)
                    .onFailure {
                        _state.update { state ->
                            state.success().copy(comments = beforeData)
                        }
                    }
            }
        }

        private fun editComment(
            targetId: Int,
            comment: String,
        ) {
            val beforeData = state.successData().comments
            val updated = state.successData().comments.replaceContentMessage(
                targetId = targetId,
                updatedComment = comment,
            )
            _state.value = state.value.success().copy(comments = updated)
            viewModelScope.launch {
                commentRepository.updateComment(
                    commentId = targetId,
                    content = comment,
                ).onSuccess {
                    _event.send(CommentEvent.SuccessSending)
                    setEmptyTextState()
                }.onFailure {
                    setFilledTextState(comment)
                    _state.update { state ->
                        state.success().copy(comments = beforeData)
                    }
                }
            }
        }

        private fun uploadNewComment(newComment: String) {
            val beforeData = state.successData().comments
            val tempId = -1
            val newMessage = MessageUiModel(
                id = tempId,
                content = newComment,
                sender = SenderUiModel.MENTEE,
            )

            setEmptyTextState()
            _state.update { state ->
                val updatedComments = state.success()
                    .comments.addMessage(
                        newMessage = newMessage,
                        endDate = endDate,
                    )

                state.success().copy(
                    comments = updatedComments,
                )
            }

            viewModelScope.launch {
                commentRepository.postComment(
                    roomId = roomId,
                    content = newComment,
                ).onSuccess { newCommentId ->
                    _state.update { state ->
                        val updated = state.success().comments.replaceTempMessage(
                            tmpId = tempId,
                            newId = newCommentId,
                        )
                        state.success().copy(comments = updated)
                    }
                    _event.send(CommentEvent.SuccessSending)
                }.onFailure {
                    setFilledTextState(newComment)
                    _state.update { state ->
                        state.success().copy(comments = beforeData)
                    }
                }
            }
        }

        private fun setEmptyTextState() {
            commentContent = ""
            _state.update { state ->
                state.success().copy(commentTextState = CommentTextState.Empty)
            }
        }

        private fun setFilledTextState(
            newContent: String,
            textState: CommentTextState = CommentTextState.Filled,
        ) {
            commentContent = newContent
            _state.update { state ->
                state.success().copy(commentTextState = textState)
            }
        }

        companion object {
            private const val MAXIMUM_MESSAGE_LENGTH = 300
        }
    }
