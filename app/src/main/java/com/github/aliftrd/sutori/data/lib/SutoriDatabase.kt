package com.github.aliftrd.sutori.data.lib

import androidx.room.Database
import androidx.room.RoomDatabase
import com.github.aliftrd.sutori.data.remote_keys.dao.RemoteKeysDao
import com.github.aliftrd.sutori.data.remote_keys.dto.RemoteKeys
import com.github.aliftrd.sutori.data.story.dao.StoryDao
import com.github.aliftrd.sutori.data.story.dto.StoryItem

@Database(entities = [StoryItem::class, RemoteKeys::class], version = 1)
abstract class SutoriDatabase: RoomDatabase() {
    abstract fun storyDao(): StoryDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}