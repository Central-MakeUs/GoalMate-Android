package cmc.goalmate.presentation.ui.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cmc.goalmate.domain.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class LoginStateViewModel(
    private val authRepository: AuthRepository,
) : ViewModel() {
    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()

    protected fun observeLoginStatus() {
        viewModelScope.launch {
            authRepository.isLogin()
                .collect { isLogIn ->
                    _isLoggedIn.value = isLogIn
                    onLoginStateChanged(isLogIn)
                }
        }
    }

    protected open fun onLoginStateChanged(isLoggedIn: Boolean) {}
}
