package com.example.moodify.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moodify.model.response.JournalItem
import com.example.moodify.repository.MoodifyRepository
import com.example.moodify.model.response.Result
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: MoodifyRepository) : ViewModel() {
    private val resultApi = MutableLiveData<Result<String>>()

    fun login(email: String, password: String): LiveData<Result<String>> {
        resultApi.value = Result.Loading
        viewModelScope.launch {
            resultApi.value = repository.login(email, password).value
        }
        return resultApi
    }

}