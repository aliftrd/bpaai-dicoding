package com.github.aliftrd.sutori.ui.story.add

import android.net.Uri
import androidx.core.net.toFile
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.aliftrd.sutori.data.lib.ApiResponse
import com.github.aliftrd.sutori.data.story.StoryRepository
import androidx.lifecycle.viewModelScope
import com.github.aliftrd.sutori.data.story.dto.AddStoryRequest
import com.github.aliftrd.sutori.data.story.dto.AddStoryResponse
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class AddStoryViewModel(private val repository: StoryRepository) : ViewModel() {
    private val _addStoryResult = MutableLiveData<ApiResponse<AddStoryResponse>>()
    val addStoryResult: LiveData<ApiResponse<AddStoryResponse>> by lazy { _addStoryResult }

    fun uploadStory(photo: Uri, description: String) {
        val photoFile = photo.toFile()
        val requestImageFile = photoFile.asRequestBody("image/*".toMediaType())
        val requestBody = description.toRequestBody("text/plain".toMediaType())
        val multipartBody = MultipartBody.Part.createFormData("photo", photoFile.name, requestImageFile)

        viewModelScope.launch {
            val dto = AddStoryRequest(multipartBody, requestBody)
            repository.upload(dto).collect { it ->
                _addStoryResult.value = it
            }
        }
    }
}