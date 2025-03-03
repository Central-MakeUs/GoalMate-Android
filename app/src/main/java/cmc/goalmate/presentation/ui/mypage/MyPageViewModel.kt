package cmc.goalmate.presentation.ui.mypage

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import cmc.goalmate.domain.DomainResult
import cmc.goalmate.domain.fold
import cmc.goalmate.domain.onFailure
import cmc.goalmate.domain.onSuccess
import cmc.goalmate.domain.repository.AuthRepository
import cmc.goalmate.domain.repository.UserRepository
import cmc.goalmate.presentation.ui.common.LoginStateViewModel
import cmc.goalmate.presentation.ui.mypage.model.MyPageUiModel
import cmc.goalmate.presentation.ui.mypage.model.NicknameState
import cmc.goalmate.presentation.ui.mypage.model.toUi
import cmc.goalmate.presentation.ui.util.EventBus
import cmc.goalmate.presentation.ui.util.GoalMateEvent
import cmc.goalmate.presentation.ui.util.asUiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel
    @Inject
    constructor(
        private val authRepository: AuthRepository,
        private val userRepository: UserRepository,
    ) : LoginStateViewModel(authRepository) {
        private val _state = MutableStateFlow<MyPageUiState>(MyPageUiState.Loading)
        val state: StateFlow<MyPageUiState> = _state.asStateFlow()

        private val _event = Channel<MyPageEvent>()
        val event = _event.receiveAsFlow()

        var nickName by mutableStateOf("")
            private set

        init {
            observeLoginStatus()
            observeGoalMateEvent()
        }

        override fun onLoginStateChanged(isLoggedIn: Boolean) {
            if (isLoggedIn) {
                loadUserInfo()
            } else {
                setLogoutState()
            }
        }

        private fun observeGoalMateEvent() {
            viewModelScope.launch {
                EventBus.subscribeEvent<GoalMateEvent.StartNewGoal> {
                    inCreaseOnGoingGoalCount()
                }
            }
        }

        private fun inCreaseOnGoingGoalCount() {
            val current = state.value.successData()
            val updatedUserInfo = current.userInfo.copy(
                onGoingGoalCount = current.userInfo.onGoingGoalCount + 1,
            )
            val updatedState = current.copy(userInfo = updatedUserInfo)
            _state.update { updatedState }
        }

        private fun loadUserInfo() {
            viewModelScope.launch {
                val updated = when (val result = userRepository.getUserInfo()) {
                    is DomainResult.Success -> {
                        val userInfoUi = result.data.toUi()
                        nickName = userInfoUi.nickName
                        MyPageUiState.Success(
                            userInfo = userInfoUi,
                            isLoggedIn = true,
                            nicknameState = NicknameState.Unchanged,
                        )
                    }

                    is DomainResult.Error -> {
                        MyPageUiState.Error
                    }
                }
                _state.update { updated }
            }
        }

        private fun setLogoutState() {
            _state.update {
                MyPageUiState.Success(
                    userInfo = MyPageUiModel.DEFAULT_INFO,
                    isLoggedIn = false,
                    nicknameState = NicknameState.Idle,
                )
            }
        }

        private fun sendEvent(event: MyPageEvent) {
            viewModelScope.launch {
                _event.send(event)
            }
        }

        fun onAction(menuAction: MyPageAction) {
            when (menuAction) {
                MyPageAction.MenuAction.DeleteAccount -> deleteAccount()
                MyPageAction.MenuAction.Logout -> logout()
                MyPageAction.MenuAction.FAQ -> sendEvent(MyPageEvent.ShowFAQ)
                MyPageAction.MenuAction.PrivacyPolicy -> sendEvent(MyPageEvent.ShowPrivacyPolicy)
                MyPageAction.MenuAction.TermsOfService -> sendEvent(MyPageEvent.ShowTermsOfService)
                MyPageAction.MenuAction.EditNickName -> editNickName()
                is MyPageAction.CheckDuplication -> checkDuplication(menuAction.updated)
                is MyPageAction.ConfirmNickName -> confirmNewNickName(menuAction.updated)
                is MyPageAction.SetNickName -> {
                    nickName = menuAction.updated
                    checkValidity(nickName)
                }
            }
        }

        private fun logout() {
            viewModelScope.launch {
                authRepository
                    .logout()
                    .onSuccess { _event.send(MyPageEvent.SuccessLogout) }
            }
        }

        private fun deleteAccount() {
            viewModelScope.launch {
                authRepository
                    .deleteToken()
                    .onSuccess { _event.send(MyPageEvent.SuccessDeleteAccount) }
            }
        }

        private fun editNickName() {
            val event = if (state.value.isLoggedIn()) MyPageEvent.EditNickName else MyPageEvent.NeedLogin
            sendEvent(event)
        }

        private fun checkValidity(newNickName: String) {
            if (newNickName == state.value.successData().userInfo.nickName) {
                _state.update { current ->
                    (current as MyPageUiState.Success).copy(nicknameState = NicknameState.Unchanged)
                }
                return
            }

            val updatedNicknameState = userRepository.checkNicknameValidity(newNickName)
                .fold(
                    onSuccess = { NicknameState.CanCheckDuplication },
                    onFailure = { NicknameState.InValid(it.asUiText()) },
                )
            _state.update { current ->
                (current as MyPageUiState.Success).copy(nicknameState = updatedNicknameState)
            }
        }

        private fun checkDuplication(newNickName: String) {
            viewModelScope.launch {
                val updatedNicknameState = userRepository.checkNicknameAvailable(newNickName)
                    .fold(
                        onSuccess = { NicknameState.Available("사용 가능한 닉네임이에요 :)") },
                        onFailure = { NicknameState.InValid(it.asUiText()) },
                    )

                _state.update { current ->
                    (current as MyPageUiState.Success).copy(nicknameState = updatedNicknameState)
                }
            }
        }

        private fun confirmNewNickName(newNickName: String) {
            viewModelScope.launch {
                userRepository.updateNickName(newNickName)
                    .onSuccess {
                        _state.update { current ->
                            val updatedUserInfo = current.successData().userInfo.copy(nickName = newNickName)
                            (current as MyPageUiState.Success).copy(nicknameState = NicknameState.Unchanged, userInfo = updatedUserInfo)
                        }
                        sendEvent(MyPageEvent.SuccessChangeNickName)
                    }
                    .onFailure {
                    }
            }
        }
    }
