package com.github.aliftrd.sutori.ui.story

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.aliftrd.sutori.data.lib.ApiResponse
import com.github.aliftrd.sutori.data.story.StoryRepository
import com.github.aliftrd.sutori.data.story.dto.GetStoryParam
import com.github.aliftrd.sutori.data.story.dto.StoryResponse
import kotlinx.coroutines.launch

class StoryViewModel(private val repository: StoryRepository) : ViewModel() {
    private val page: Int = 1
    private val size: Int = 5

    private val _story = MutableLiveData<ApiResponse<StoryResponse>>()
    val story: LiveData<ApiResponse<StoryResponse>> by lazy { _story }

    fun get() {
        viewModelScope.launch {
            repository.getAll(GetStoryParam(page, size)).collect { it ->
                _story.value = it
            }
        }
    }
}