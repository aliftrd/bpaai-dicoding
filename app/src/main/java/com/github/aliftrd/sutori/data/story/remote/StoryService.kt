package com.github.aliftrd.sutori.data.story.remote

import com.github.aliftrd.sutori.data.story.dto.AddStoryResponse
import com.github.aliftrd.sutori.data.story.dto.StoryResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface StoryService {
    @GET("stories")
    suspend fun getAll(
        @Query("page")
        page: Int?,

        @Query("size")
        size: Int?,

        @Query("location")
        location: Int?
    ): StoryResponse

    @Multipart
    @POST("stories")
    suspend fun upload(
        @Part
        photo: MultipartBody.Part,

        @Part("description")
        description: RequestBody

    ): AddStoryResponse

}