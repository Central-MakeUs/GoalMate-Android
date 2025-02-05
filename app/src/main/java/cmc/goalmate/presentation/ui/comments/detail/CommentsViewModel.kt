package cmc.goalmate.presentation.ui.comments.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

data class CommentsUiState(
    val comments: List<CommentUiModel>,
)

@HiltViewModel
class CommentsViewModel
    @Inject
    constructor(
        savedStateHandle: SavedStateHandle,
    ) : ViewModel() {
//        private val _state = MutableStateFlow<CommentsUiState>(CommentsUiState.initialValue)
//        val state: StateFlow<CommentsUiState>
//            get() = _state
    }
