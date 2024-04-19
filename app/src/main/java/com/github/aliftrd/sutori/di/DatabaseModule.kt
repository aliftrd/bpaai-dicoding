package com.github.aliftrd.sutori.di

import androidx.room.Room
import com.github.aliftrd.sutori.data.lib.SutoriDatabase
import com.github.aliftrd.sutori.utils.ConstVar
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            SutoriDatabase::class.java,
            ConstVar.RUNNING_DATABASE_NAME
        ).build()
    }

    single { get<SutoriDatabase>().storyDao() }
    single { get<SutoriDatabase>().remoteKeysDao() }
}