package cmc.goalmate.presentation.ui.mypage

import cmc.goalmate.presentation.components.InputTextState
import cmc.goalmate.presentation.ui.mypage.model.MyPageUiModel
import cmc.goalmate.presentation.ui.mypage.model.NicknameState
import cmc.goalmate.presentation.ui.mypage.model.toInputTextState

sealed interface MyPageUiState {
    data object Loading : MyPageUiState

    data class Success(
        val userInfo: MyPageUiModel,
        val isLoggedIn: Boolean,
        val nicknameState: NicknameState = NicknameState.Idle,
    ) : MyPageUiState {
        val inputTextState: InputTextState
            get() = nicknameState.toInputTextState()

        val canCheckDuplication: Boolean
            get() = nicknameState == NicknameState.CanCheckDuplication

        val helperText: String
            get() = (nicknameState as? NicknameState.InValid)?.errorText
                ?: (nicknameState as? NicknameState.Available)?.successText ?: ""
    }

    data object Error : MyPageUiState
}

fun MyPageUiState.isLoggedIn(): Boolean = (this as? MyPageUiState.Success)?.isLoggedIn == true

fun MyPageUiState.successData(): MyPageUiState.Success = this as MyPageUiState.Success
