package com.example.moodify.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moodify.model.response.CopingResponse
import com.example.moodify.model.response.Result
import com.example.moodify.repository.MoodifyRepository

class CopingViewModel(private val repository: MoodifyRepository) : ViewModel() {

    fun getCoping(): LiveData<Result<CopingResponse>> {
        return repository.getCoping()
    }
}