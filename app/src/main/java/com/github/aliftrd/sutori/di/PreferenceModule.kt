package com.github.aliftrd.sutori.di

import com.github.aliftrd.sutori.utils.PreferenceManager
import org.koin.dsl.module

val preferenceModule = module {
    single { PreferenceManager(get()) }
}