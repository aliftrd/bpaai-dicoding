package com.github.aliftrd.sutori.base

import android.app.Application
import com.github.aliftrd.sutori.di.databaseModule
import com.github.aliftrd.sutori.di.preferenceModule
import com.github.aliftrd.sutori.di.feature.authModule
import com.github.aliftrd.sutori.di.feature.storyModule
import com.github.aliftrd.sutori.di.networkModule
import com.github.aliftrd.sutori.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.logger.Level

class BaseApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@BaseApplication)
            modules(
                listOf(
                    authModule,
                    storyModule,
                    databaseModule,
                    networkModule,
                    preferenceModule,
                    viewModelModule,
                )
            )
        }
    }

    override fun onTerminate() {
        stopKoin()
        super.onTerminate()
    }
}