package com.newton.auth.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newton.domain.repository.auth.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel
@Inject
constructor(
    private val authRepository: AuthRepository,
) : ViewModel() {
    private val _hasTokens = MutableStateFlow<Boolean?>(null)
    val hasTokens: StateFlow<Boolean?> = _hasTokens.asStateFlow()

    fun checkTokens() {
        viewModelScope.launch {
            try {
                val accessToken = authRepository.getAccessToken()
                val refreshToken = authRepository.getRefreshToken()

                val hasValidTokens = !accessToken.isNullOrBlank() && !refreshToken.isNullOrBlank()
                _hasTokens.value = hasValidTokens
            } catch (e: Exception) {
                _hasTokens.value = false
            }
        }
    }
}
