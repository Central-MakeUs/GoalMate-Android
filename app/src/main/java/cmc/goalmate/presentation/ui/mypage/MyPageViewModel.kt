package cmc.goalmate.presentation.ui.mypage

import cmc.goalmate.domain.repository.UserRepository
import cmc.goalmate.presentation.ui.common.LoginStateViewModel
import cmc.goalmate.presentation.ui.common.UserState
import cmc.goalmate.presentation.ui.mypage.MyPageUiState.Companion.initialMyPageUiState
import cmc.goalmate.presentation.ui.mypage.model.MyPageUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

data class MyPageUiState(
    val isLoading: Boolean,
    val userGoalsState: UserState<MyPageUiModel>,
) {
    companion object {
        fun initialMyPageUiState(): MyPageUiState = MyPageUiState(isLoading = true, userGoalsState = UserState.LoggedOut)
    }
}

@HiltViewModel
class
MyPageViewModel@Inject
    constructor(userRepository: UserRepository) : LoginStateViewModel(userRepository) {
        private val _state = MutableStateFlow(initialMyPageUiState())
        val state: StateFlow<MyPageUiState>
            get() = _state

        init {
            if (isLoggedIn.value) {
                // 사용자 정보 가져오기
            } else {
                _state.value = MyPageUiState(isLoading = false, userGoalsState = UserState.LoggedOut)
            }
        }
    }
