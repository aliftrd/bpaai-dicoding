package com.github.aliftrd.sutori.data.story.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.github.aliftrd.sutori.data.story.dto.StoryItem

@Dao
interface StoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStory(story: List<StoryItem>)

    @Query("SELECT * FROM stories ORDER BY createdAt DESC")
    fun getAllStory(): PagingSource<Int, StoryItem>

    @Query("DELETE FROM stories")
    suspend fun deleteAll()
}