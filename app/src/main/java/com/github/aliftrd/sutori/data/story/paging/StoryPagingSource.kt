package com.github.aliftrd.sutori.data.story.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.github.aliftrd.sutori.data.story.dto.StoryItem
import com.github.aliftrd.sutori.data.story.remote.StoryService

class StoryPagingSource(private val storyService: StoryService):PagingSource<Int, StoryItem>() {
    override fun getRefreshKey(state: PagingState<Int, StoryItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, StoryItem> {
        return try {
            val position = params.key ?: STARTING_PAGE_INDEX
            val responseData = storyService.getAll(position, params.loadSize, 0)
            LoadResult.Page(
                data = responseData.listStory,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (responseData.listStory.isEmpty()) null else position + 1
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    companion object {
        private const val STARTING_PAGE_INDEX = 1
    }
}