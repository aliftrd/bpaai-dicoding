package com.github.aliftrd.sutori.data.auth

import com.github.aliftrd.sutori.data.auth.dto.LoginRequest
import com.github.aliftrd.sutori.data.auth.dto.LoginResponse
import com.github.aliftrd.sutori.data.auth.dto.RegisterRequest
import com.github.aliftrd.sutori.data.auth.dto.RegisterResponse
import com.github.aliftrd.sutori.data.auth.remote.AuthService
import com.github.aliftrd.sutori.data.lib.ApiResponse
import com.github.aliftrd.sutori.di.networkModule
import com.github.aliftrd.sutori.di.preferenceModule
import com.github.aliftrd.sutori.utils.PreferenceManager
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import retrofit2.HttpException

interface AuthRepository {
    fun register(dto: RegisterRequest): Flow<ApiResponse<RegisterResponse>>
    fun login(dto: LoginRequest): Flow<ApiResponse<LoginResponse>>
}

class AuthRepositoryImpl(private val api: AuthService, private val prefs: PreferenceManager) : AuthRepository {
    override fun register(dto: RegisterRequest): Flow<ApiResponse<RegisterResponse>> = flow {
        try {
            emit(ApiResponse.Loading)
            val response = api.register(dto.name, dto.email, dto.password)
            if (!response.error) {
                emit(ApiResponse.Success(response))
            } else {
                emit(ApiResponse.Error(response.message))
            }
        } catch (e: Exception) {
            if (e is HttpException) {
                val errorMessage = Gson().fromJson(
                    e.response()?.errorBody()?.string(),
                    RegisterResponse::class.java
                )
                emit(ApiResponse.Error(errorMessage.message))
            } else {
                e.printStackTrace()
                emit(ApiResponse.Error("Unknown error"))
            }
        }
    }

    override fun login(dto: LoginRequest): Flow<ApiResponse<LoginResponse>> = flow {
        try {
            emit(ApiResponse.Loading)
            val response = api.login(dto.email, dto.password)
            if (!response.error) {
                val userItem = response.loginResult
                prefs.setLoginPref(userItem)

                reloadModule()
                emit(ApiResponse.Success(response))
            } else {
                emit(ApiResponse.Error(response.message))
            }
        } catch (e: Exception) {
            if (e is HttpException) {
                val errorMessage =
                    Gson().fromJson(e.response()?.errorBody()?.string(), LoginResponse::class.java)
                emit(ApiResponse.Error(errorMessage.message))
            } else {
                e.printStackTrace()
                emit(ApiResponse.Error("Unknown error"))
            }
        }
    }

    private fun reloadModule() {
        unloadKoinModules(preferenceModule)
        loadKoinModules(preferenceModule)
        unloadKoinModules(networkModule)
        loadKoinModules(networkModule)
    }
}