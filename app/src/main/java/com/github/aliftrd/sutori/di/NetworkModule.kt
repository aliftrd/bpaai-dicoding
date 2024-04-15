package com.github.aliftrd.sutori.di

import com.github.aliftrd.sutori.data.auth.remote.AuthService
import com.github.aliftrd.sutori.data.lib.HeaderInterceptor
import com.github.aliftrd.sutori.data.story.remote.StoryService
import com.github.aliftrd.sutori.utils.PreferenceManager
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val baseUrl = "https://story-api.dicoding.dev/v1/"
val networkModule = module {
    single {
        return@single OkHttpClient.Builder()
            .addInterceptor(getHeaderInterceptor(get()))
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
    }

    single { provideAuthService(get()) }
    single { provideStoryService(get()) }

}

private fun getHeaderInterceptor(preferenceManager: PreferenceManager): Interceptor {
    val headers = HashMap<String, String>()
    headers["Content-Type"] = "application/json"

    return HeaderInterceptor(headers, preferenceManager)
}

fun provideAuthService(retrofit: Retrofit): AuthService = retrofit.create(AuthService::class.java)
fun provideStoryService(retrofit: Retrofit): StoryService = retrofit.create(StoryService::class.java)