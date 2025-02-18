package cmc.goalmate.presentation.ui.mypage

import androidx.lifecycle.viewModelScope
import cmc.goalmate.domain.DomainResult
import cmc.goalmate.domain.repository.AuthRepository
import cmc.goalmate.domain.repository.UserRepository
import cmc.goalmate.presentation.ui.common.LoginStateViewModel
import cmc.goalmate.presentation.ui.mypage.model.MyPageUiModel
import cmc.goalmate.presentation.ui.mypage.model.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

sealed interface MyPageUiState {
    data object Loading : MyPageUiState

    data class LoggedIn(val userInfo: MyPageUiModel) : MyPageUiState

    data object LoggedOut : MyPageUiState

    data object Error : MyPageUiState
}

fun MyPageUiState.isLoggedIn(): Boolean = this is MyPageUiState.LoggedIn

@HiltViewModel
class MyPageViewModel
    @Inject
    constructor(authRepository: AuthRepository, private val userRepository: UserRepository) :
    LoginStateViewModel(authRepository) {
        val state: StateFlow<MyPageUiState> = isLoggedIn
            .map { isLoggedIn ->
                if (isLoggedIn) {
                    when (val result = userRepository.getUserInfo()) {
                        is DomainResult.Success -> MyPageUiState.LoggedIn(result.data.toUi())
                        is DomainResult.Error -> MyPageUiState.Error
                    }
                } else {
                    MyPageUiState.LoggedOut
                }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = MyPageUiState.Loading,
            )
    }
