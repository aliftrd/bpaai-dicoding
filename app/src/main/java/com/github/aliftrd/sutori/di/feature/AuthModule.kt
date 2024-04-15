package com.github.aliftrd.sutori.di.feature

import com.github.aliftrd.sutori.data.auth.AuthRepository
import com.github.aliftrd.sutori.data.auth.AuthRepositoryImpl
import org.koin.dsl.module

val authModule = module {
    single<AuthRepository> { AuthRepositoryImpl(get(), get()) }
}