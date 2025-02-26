package cmc.goalmate.presentation.ui.mypage.model

import cmc.goalmate.presentation.components.InputTextState

sealed interface NicknameState {
    data object Idle : NicknameState

    data object Unchanged : NicknameState

    data object CanCheckDuplication : NicknameState

    data class InValid(val errorText: String) : NicknameState

    data class Available(val successText: String) : NicknameState
}

fun NicknameState.toInputTextState(): InputTextState =
    when (this) {
        is NicknameState.Idle, NicknameState.Unchanged, is NicknameState.CanCheckDuplication -> InputTextState.None
        is NicknameState.InValid -> InputTextState.Error
        is NicknameState.Available -> InputTextState.Success
    }
