package com.github.aliftrd.sutori.ui.story

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.github.aliftrd.sutori.data.story.StoryRepository
import com.github.aliftrd.sutori.data.story.dto.GetStoryParam
import com.github.aliftrd.sutori.data.story.dto.StoryItem

class StoryViewModel(private val repository: StoryRepository) : ViewModel() {
    private val _story = MutableLiveData<PagingData<StoryItem>>()
    val story: LiveData<PagingData<StoryItem>> = _story

    fun getStory() {
        repository.getAll(GetStoryParam(size = 5))
            .cachedIn(viewModelScope)
            .observeForever {
                _story.value = it
            }
    }
}