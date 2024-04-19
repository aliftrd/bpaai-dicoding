package com.github.aliftrd.sutori

import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.github.aliftrd.sutori.data.story.dto.StoryItem

class paggingData : PagingSource<Int, StoryItem>() {
    override fun getRefreshKey(state: PagingState<Int, StoryItem>): Int = 0

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, StoryItem> =
        LoadResult.Page(emptyList(),0,1)

    companion object {
        fun snapshot(items: List<StoryItem>): PagingData<StoryItem> {
            return PagingData.from(items)
        }
    }
}