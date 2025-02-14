package cmc.goalmate.presentation.ui.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cmc.goalmate.domain.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class LoginStateViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> get() = _isLoggedIn.asStateFlow()

    init {
        viewModelScope.launch {
            userRepository.isLogin().collect { isLogin -> _isLoggedIn.value = isLogin }
        }
    }
}
