package cmc.goalmate.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cmc.goalmate.domain.DataError
import cmc.goalmate.domain.DomainResult
import cmc.goalmate.domain.onSuccess
import cmc.goalmate.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
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

                delay(1000L)

                if (!hasToken) {
                    _isReady.value = true
                    return@launch
                }

                when (val result = authRepository.validateToken()) {
                    is DomainResult.Error -> {
                        if (result.error == DataError.Network.NO_INTERNET) {
                            _isReady.value = true
                            return@launch
                        }
                        authRepository
                            .deleteToken()
                            .onSuccess {
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
