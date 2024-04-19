package com.github.aliftrd.sutori.data.story.dto

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.github.aliftrd.sutori.data.lib.BaseResponse
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import okhttp3.MultipartBody
import okhttp3.RequestBody

data class StoryResponse(
    @field:SerializedName("listStory")
    val listStory: List<StoryItem>,
) : BaseResponse()

@Parcelize
@Entity(tableName = "stories")
data class StoryItem(
    @PrimaryKey
    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("photoUrl")
    val photoUrl: String,

    @field:SerializedName("createdAt")
    val createdAt: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("description")
    val description: String,

    @field:SerializedName("lon")
    val lon: Float,

    @field:SerializedName("lat")
    val lat: Float
): Parcelable

data class GetStoryParam(
    val page: Int? = null,
    val size: Int? = null,
    val location: Int? = 0,
)

class AddStoryResponse(): BaseResponse()

data class AddStoryRequest(
    val photo: MultipartBody.Part,
    val description: RequestBody,
)