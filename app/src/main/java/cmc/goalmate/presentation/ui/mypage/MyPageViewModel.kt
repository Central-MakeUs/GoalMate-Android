package cmc.goalmate.presentation.ui.mypage

import androidx.lifecycle.viewModelScope
import cmc.goalmate.domain.DomainResult
import cmc.goalmate.domain.onSuccess
import cmc.goalmate.domain.repository.AuthRepository
import cmc.goalmate.domain.repository.UserRepository
import cmc.goalmate.presentation.ui.common.LoginStateViewModel
import cmc.goalmate.presentation.ui.mypage.model.MyPageUiModel
import cmc.goalmate.presentation.ui.mypage.model.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface MyPageUiState {
    data object Loading : MyPageUiState

    data class Success(val userInfo: MyPageUiModel, val isLoggedIn: Boolean) : MyPageUiState

    data object Error : MyPageUiState
}

fun MyPageUiState.isLoggedIn(): Boolean = (this as? MyPageUiState.Success)?.isLoggedIn == true

@HiltViewModel
class MyPageViewModel
    @Inject
    constructor(
        private val authRepository: AuthRepository,
        private val userRepository: UserRepository,
    ) :
    LoginStateViewModel(authRepository) {
        val state: StateFlow<MyPageUiState> = isLoggedIn
            .map { isLoggedIn ->
                if (isLoggedIn) {
                    when (val result = userRepository.getUserInfo()) {
                        is DomainResult.Success -> MyPageUiState.Success(result.data.toUi(), true)
                        is DomainResult.Error -> MyPageUiState.Error
                    }
                } else {
                    MyPageUiState.Success(MyPageUiModel.DEFAULT_INFO, false)
                }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = MyPageUiState.Loading,
            )

        private val _event = Channel<MyPageEvent>()
        val event = _event.receiveAsFlow()

        private fun sendEvent(event: MyPageEvent) {
            viewModelScope.launch {
                _event.send(event)
            }
        }

        fun onAction(menuAction: MenuAction) {
            when (menuAction) {
                MenuAction.DeleteAccount -> deleteAccount()
                MenuAction.Logout -> logout()
                MenuAction.FAQ -> sendEvent(MyPageEvent.ShowFAQ)
                MenuAction.PrivacyPolicy -> sendEvent(MyPageEvent.ShowPrivacyPolicy)
                MenuAction.TermsOfService -> sendEvent(MyPageEvent.ShowTermsOfService)
                is MenuAction.ConfirmNewNickName -> {}
                MenuAction.EditNickName -> editNickName()
            }
        }

        private fun logout() {
            viewModelScope.launch {
                authRepository.logout()
                    .onSuccess { _event.send(MyPageEvent.SuccessLogout) }
            }
        }

        private fun deleteAccount() {
            viewModelScope.launch {
                authRepository.deleteToken()
                    .onSuccess { _event.send(MyPageEvent.SuccessDeleteAccount) }
            }
        }

        private fun editNickName() {
            val event = if (isLoggedIn.value) MyPageEvent.EditNickName else MyPageEvent.NeedLogin
            sendEvent(event)
        }
    }

sealed interface MenuAction {
    data object FAQ : MenuAction

    data object PrivacyPolicy : MenuAction

    data object TermsOfService : MenuAction

    data object Logout : MenuAction

    data object DeleteAccount : MenuAction

    data object EditNickName : MenuAction

    data class ConfirmNewNickName(val content: String) : MenuAction
}

sealed interface MyPageEvent {
    data object ShowFAQ : MyPageEvent

    data object ShowTermsOfService : MyPageEvent

    data object ShowPrivacyPolicy : MyPageEvent

    data object SuccessLogout : MyPageEvent

    data object SuccessDeleteAccount : MyPageEvent

    data object EditNickName : MyPageEvent

    data object NeedLogin : MyPageEvent
}
