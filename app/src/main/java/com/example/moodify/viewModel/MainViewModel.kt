package com.example.moodify.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.moodify.Repository.MoodifyRepository
import com.example.moodify.retrofit.UserModel
import kotlinx.coroutines.launch

class MainViewModel(private val repository: MoodifyRepository) : ViewModel() {

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }
}