package com.github.aliftrd.sutori

import com.github.aliftrd.sutori.data.story.dto.StoryItem

object DataDummy {

    fun generateNewStory(): List<StoryItem> {
        val items: MutableList<StoryItem> = arrayListOf()
        for (i in 0..100) {
            val story = StoryItem(
                id = i.toString(),
                name = "Story $i",
                description = "Description $i",
                photoUrl = "https://picsum.photos/200/300?random=$i",
                createdAt = "2021-01-01T00:00:00Z",
                lat = (-7.78).toFloat(),
                lon = 114.04.toFloat()
            )
            items.add(story)
        }
        return items
    }
}