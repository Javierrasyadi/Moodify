package com.example.moodify.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moodify.model.response.Result
import com.example.moodify.repository.MoodifyRepository
import kotlinx.coroutines.launch

class MyJournalViewModel(private val repository: MoodifyRepository) : ViewModel() {
    private val result = MutableLiveData<Result<String>>()
    fun addJournal(title: String, desc: String, mood: String): LiveData<Result<String>> {
        result.value = Result.Loading
        viewModelScope.launch {
            result.value = repository.addJournal(title, desc, mood).value
        }
        return result
    }
}