package com.example.moodify.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.moodify.model.response.MusicCategoryResponse
import com.example.moodify.model.response.Result
import com.example.moodify.repository.MoodifyRepository

class MusicViewModel(private val repository: MoodifyRepository): ViewModel() {
    fun getMusic(): LiveData<Result<MusicCategoryResponse>>{
        return repository.getMusic()
    }
}