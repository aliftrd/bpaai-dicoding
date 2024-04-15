package com.github.aliftrd.sutori.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.aliftrd.sutori.data.auth.AuthRepository
import com.github.aliftrd.sutori.data.auth.dto.RegisterRequest
import com.github.aliftrd.sutori.data.auth.dto.RegisterResponse
import com.github.aliftrd.sutori.data.lib.ApiResponse
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: AuthRepository) : ViewModel() {
    private val _registerResult = MutableLiveData<ApiResponse<RegisterResponse>>()
    val registerResult: LiveData<ApiResponse<RegisterResponse>> by lazy { _registerResult }

    fun register(name: String, email: String, password: String) {
        viewModelScope.launch {
            repository.register(RegisterRequest(name, email, password)).collect {
                _registerResult.value = it
            }
        }
    }
}