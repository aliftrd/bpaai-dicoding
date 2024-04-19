package com.github.aliftrd.sutori.di.feature

import com.github.aliftrd.sutori.data.story.StoryRepository
import com.github.aliftrd.sutori.data.story.StoryRepositoryImpl
import org.koin.dsl.module

val storyModule = module {
    single<StoryRepository> { StoryRepositoryImpl(get(), get()) }
}