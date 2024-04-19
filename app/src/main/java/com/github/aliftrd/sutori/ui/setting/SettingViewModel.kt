package com.github.aliftrd.sutori.ui.setting

import androidx.lifecycle.ViewModel
import com.github.aliftrd.sutori.data.auth.AuthRepository

class SettingViewModel (private val repository: AuthRepository): ViewModel() {
    fun logout() = repository.logout()
}