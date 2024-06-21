package com.example.moodify.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.moodify.model.response.PodcastCategoryResponse
import com.example.moodify.model.response.Result
import com.example.moodify.repository.MoodifyRepository

class PodcastViewModel(private val repository: MoodifyRepository): ViewModel() {
    fun getPodcast(): LiveData<Result<PodcastCategoryResponse>>{
        return repository.getPodcast()
    }
}