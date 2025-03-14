package cmc.goalmate.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cmc.goalmate.domain.DomainResult
import cmc.goalmate.domain.onSuccess
import cmc.goalmate.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel
    @Inject
    constructor(
        private val authRepository: AuthRepository,
    ) : ViewModel() {
        private val _isReady = MutableStateFlow(false)
        val isReady: StateFlow<Boolean> = _isReady.asStateFlow()

        fun checkLoginStatus() {
            viewModelScope.launch {
                val hasToken = authRepository.isLogin().first()

                if (!hasToken) {
                    _isReady.value = true
                    return@launch
                }

                when (authRepository.validateToken()) {
                    is DomainResult.Error -> {
                        authRepository.deleteToken().onSuccess {
                            _isReady.value = true
                        }
                    }
                    is DomainResult.Success -> {
                        _isReady.value = true
                    }
                }
            }
        }
    }
