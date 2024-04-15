package com.github.aliftrd.sutori.data.auth.remote

import com.github.aliftrd.sutori.data.auth.dto.LoginResponse
import com.github.aliftrd.sutori.data.auth.dto.RegisterResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthService {
    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("name")
        name: String,

        @Field("email")
        email: String,

        @Field("password")
        password: String
    ): RegisterResponse

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email")
        email: String,

        @Field("password")
        password: String
    ): LoginResponse
}