package cmc.goalmate.presentation.ui.progress.comments

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import cmc.goalmate.presentation.ui.progress.components.CommentUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

data class CommentsUiState(
    val comments: List<CommentUiModel>,
) {
    companion object {
        val initialValue = CommentsUiState(listOf(CommentUiModel.DUMMY, CommentUiModel.DUMMY))
    }
}

@HiltViewModel
class CommentsViewModel
    @Inject
    constructor(
        savedStateHandle: SavedStateHandle,
    ) : ViewModel() {
        private val _state = MutableStateFlow<CommentsUiState>(CommentsUiState.initialValue)
        val state: StateFlow<CommentsUiState>
            get() = _state
    }
