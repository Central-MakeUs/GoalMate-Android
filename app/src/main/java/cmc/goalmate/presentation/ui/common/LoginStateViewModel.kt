package cmc.goalmate.presentation.ui.common

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cmc.goalmate.domain.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

abstract class LoginStateViewModel(private val authRepository: AuthRepository) : ViewModel() {
    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> get() = _isLoggedIn.asStateFlow()

    init {
        viewModelScope.launch {
            authRepository.isLogin()
                .distinctUntilChanged()
                .collect { isLogin ->
                    Log.d("yenny", "isLogin: $isLogin")
                    _isLoggedIn.value = isLogin
                }
        }
    }
}
