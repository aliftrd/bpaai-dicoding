package com.github.aliftrd.sutori.data.auth.dto

import com.github.aliftrd.sutori.data.lib.BaseResponse
import com.google.gson.annotations.SerializedName

// Register
data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String
)

class RegisterResponse: BaseResponse()

// Login
data class LoginRequest(
    val email: String,
    val password: String
)

data class LoginResponse(
    @field:SerializedName("loginResult")
    val loginResult: LoginResult
) : BaseResponse()

data class LoginResult(
    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("userId")
    val userId: String,

    @field:SerializedName("token")
    val token: String
)

