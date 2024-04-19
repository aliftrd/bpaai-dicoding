package com.github.aliftrd.sutori.ui.story

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.recyclerview.widget.ListUpdateCallback
import com.github.aliftrd.sutori.DataDummy
import com.github.aliftrd.sutori.MainDispatcherRule
import com.github.aliftrd.sutori.data.story.StoryRepository
import com.github.aliftrd.sutori.data.story.dto.GetStoryParam
import com.github.aliftrd.sutori.data.story.dto.StoryItem
import com.github.aliftrd.sutori.getOrAwaitValue
import com.github.aliftrd.sutori.paggingData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.KoinTest
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class StoryViewModelTest : KoinTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRules = MainDispatcherRule()

    @Mock
    private lateinit var storyRepository: StoryRepository

    @Test
    fun `when Get Story Should Not Null and Return Data`() = runTest {
        val dummyStory = DataDummy.generateNewStory()
        val data: PagingData<StoryItem> = paggingData.snapshot(dummyStory)
        val expectedStory = MutableLiveData<PagingData<StoryItem>>()
        Mockito.`when`(storyRepository.getAll(GetStoryParam(size = 5))).thenReturn(expectedStory)
        val storyViewModel = StoryViewModel(storyRepository)
        storyViewModel.getStory()
        expectedStory.value = data
        val actualStory: PagingData<StoryItem> = storyViewModel.story.getOrAwaitValue()

        val differ = AsyncPagingDataDiffer(
            diffCallback = StoryAdapter.DIFF_CALLBACK,
            updateCallback = noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main,
        )
        differ.submitData(actualStory)

        Assert.assertNotNull(differ.snapshot())
        Assert.assertEquals(dummyStory.size, differ.snapshot().size)
        Assert.assertEquals(dummyStory[0], differ.snapshot()[0])
    }

    @Test
    fun `when Get Story Empty Should Return No Data`() = runTest {
        val data: PagingData<StoryItem> = PagingData.from(emptyList())
        val expectedStory = MutableLiveData<PagingData<StoryItem>>()
        Mockito.`when`(storyRepository.getAll(GetStoryParam(size = 5))).thenReturn(expectedStory)
        val storyViewModel = StoryViewModel(storyRepository)
        storyViewModel.getStory()
        expectedStory.value = data

        val actualStory: PagingData<StoryItem> = storyViewModel.story.getOrAwaitValue()
        val differ = AsyncPagingDataDiffer(
            diffCallback = StoryAdapter.DIFF_CALLBACK,
            updateCallback = noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main,
        )
        differ.submitData(actualStory)
        Assert.assertEquals(0, differ.snapshot().size)
    }
}

val noopListUpdateCallback = object : ListUpdateCallback {
    override fun onInserted(position: Int, count: Int) {}
    override fun onRemoved(position: Int, count: Int) {}
    override fun onMoved(fromPosition: Int, toPosition: Int) {}
    override fun onChanged(position: Int, count: Int, payload: Any?) {}
}