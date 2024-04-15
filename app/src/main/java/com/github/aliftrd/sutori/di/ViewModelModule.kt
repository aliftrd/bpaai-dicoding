package com.github.aliftrd.sutori.di

import com.github.aliftrd.sutori.ui.login.LoginViewModel
import com.github.aliftrd.sutori.ui.register.RegisterViewModel
import com.github.aliftrd.sutori.ui.story.StoryViewModel
import com.github.aliftrd.sutori.ui.story.add.AddStoryViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { RegisterViewModel(get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { StoryViewModel(get()) }
    viewModel { AddStoryViewModel(get()) }
}