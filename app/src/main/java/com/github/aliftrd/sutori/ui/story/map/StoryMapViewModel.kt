package com.github.aliftrd.sutori.ui.story.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.github.aliftrd.sutori.data.lib.ApiResponse
import com.github.aliftrd.sutori.data.story.StoryRepository
import com.github.aliftrd.sutori.data.story.dto.GetStoryParam
import com.github.aliftrd.sutori.data.story.dto.StoryItem
import com.github.aliftrd.sutori.data.story.dto.StoryResponse
import kotlinx.coroutines.launch

class StoryMapViewModel(private val repository: StoryRepository) : ViewModel() {
    private val _story = MutableLiveData<ApiResponse<StoryResponse>>()
    val story: LiveData<ApiResponse<StoryResponse>> by lazy { _story }

    fun getStory() {
        viewModelScope.launch {
            repository.getAllLocation(GetStoryParam(location = 1)).collect {
                _story.value = it
            }
        }
    }
}