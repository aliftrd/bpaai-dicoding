package com.github.aliftrd.sutori.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.aliftrd.sutori.data.auth.AuthRepository
import com.github.aliftrd.sutori.data.auth.dto.LoginRequest
import com.github.aliftrd.sutori.data.auth.dto.LoginResponse
import com.github.aliftrd.sutori.data.lib.ApiResponse
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: AuthRepository): ViewModel() {
    private val _loginResult = MutableLiveData<ApiResponse<LoginResponse>>()
    val loginResult: LiveData<ApiResponse<LoginResponse>> by lazy { _loginResult }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            repository.login(LoginRequest(email, password)).collect {
                _loginResult.value = it
            }
        }
    }
}