package com.github.aliftrd.sutori.data.story

import androidx.lifecycle.LiveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.github.aliftrd.sutori.data.lib.ApiResponse
import com.github.aliftrd.sutori.data.lib.SutoriDatabase
import com.github.aliftrd.sutori.data.story.dto.AddStoryRequest
import com.github.aliftrd.sutori.data.story.dto.AddStoryResponse
import com.github.aliftrd.sutori.data.story.dto.GetStoryParam
import com.github.aliftrd.sutori.data.story.dto.StoryItem
import com.github.aliftrd.sutori.data.story.dto.StoryResponse
import com.github.aliftrd.sutori.data.story.paging.StoryRemoteMediator
import com.github.aliftrd.sutori.data.story.remote.StoryService
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

interface StoryRepository {
    fun upload(dto: AddStoryRequest): Flow<ApiResponse<AddStoryResponse>>
    fun getAll(dto: GetStoryParam): LiveData<PagingData<StoryItem>>
    fun getAllLocation(dto: GetStoryParam): Flow<ApiResponse<StoryResponse>>
}

class StoryRepositoryImpl(private val database: SutoriDatabase, private val api: StoryService): StoryRepository {
    override fun getAll(dto: GetStoryParam): LiveData<PagingData<StoryItem>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = dto.size ?: 10,
            ),
            remoteMediator = StoryRemoteMediator(database, api),
            pagingSourceFactory = { database.storyDao().getAllStory() }
        ).liveData
    }

    override fun getAllLocation(dto: GetStoryParam): Flow<ApiResponse<StoryResponse>> = flow {
        try {
            emit(ApiResponse.Loading)
            val response = api.getAll(dto.page, dto.size, dto.location)
            if(!response.error) {
                emit(ApiResponse.Success(response))
            } else {
                emit(ApiResponse.Error(response.message))
            }
        } catch (e: Exception) {
            if(e is HttpException) {
                val errorMessage = Gson().fromJson(e.response()?.errorBody()?.string(), StoryResponse::class.java)
                emit(ApiResponse.Error(errorMessage.toString()))
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