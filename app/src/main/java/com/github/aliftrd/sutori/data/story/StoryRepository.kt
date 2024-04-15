package com.github.aliftrd.sutori.data.story

import com.github.aliftrd.sutori.data.lib.ApiResponse
import com.github.aliftrd.sutori.data.story.dto.AddStoryRequest
import com.github.aliftrd.sutori.data.story.dto.AddStoryResponse
import com.github.aliftrd.sutori.data.story.dto.GetStoryParam
import com.github.aliftrd.sutori.data.story.dto.StoryResponse
import com.github.aliftrd.sutori.data.story.remote.StoryService
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

interface StoryRepository {
    fun getAll(dto: GetStoryParam): Flow<ApiResponse<StoryResponse>>
    fun upload(dto: AddStoryRequest): Flow<ApiResponse<AddStoryResponse>>
}

class StoryRepositoryImpl(private val api: StoryService): StoryRepository {
    override fun getAll(dto: GetStoryParam): Flow<ApiResponse<StoryResponse>> = flow {
        try {
            emit(ApiResponse.Loading)
            val response = api.getAll(dto.page, dto.size)
            if(!response.error) {
                emit(ApiResponse.Success(response))
            } else {
                emit(ApiResponse.Error(response.message))
            }
        } catch (e: Exception) {
            if(e is HttpException) {
                val errorMessage = Gson().fromJson(e.response()?.errorBody()?.string(), StoryResponse::class.java)
                emit(ApiResponse.Error(errorMessage.message))
            } else {
                e.printStackTrace()
                emit(ApiResponse.Error("Unknown error"))
            }
        }
    }

    override fun upload(dto: AddStoryRequest): Flow<ApiResponse<AddStoryResponse>> = flow {
        try {
            emit(ApiResponse.Loading)
            val response = api.upload(dto.photo, dto.description)
            if(!response.error) {
                emit(ApiResponse.Success(response))
            } else {
                emit(ApiResponse.Error(response.message))
            }
        } catch (e: Exception) {
            if(e is HttpException) {
                val errorMessage = Gson().fromJson(e.response()?.errorBody()?.string(), AddStoryResponse::class.java)
                emit(ApiResponse.Error(errorMessage.message))
            } else {
                e.printStackTrace()
                emit(ApiResponse.Error("Unknown error"))
            }
        }
    }
}