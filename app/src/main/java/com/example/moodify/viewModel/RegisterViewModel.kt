package com.example.moodify.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.moodify.repository.MoodifyRepository
import com.example.moodify.retrofit.UserModel
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: MoodifyRepository): ViewModel() {
    private val resultApi = MutableLiveData<com.example.moodify.model.response.Result<String>>()

    fun signup(name : String, email: String, password: String) : LiveData<com.example.moodify.model.response.Result<String>> {
        resultApi.value = com.example.moodify.model.response.Result.Loading
        viewModelScope.launch {
            resultApi.value = repository.signUp(name,email,password).value
        }

        return resultApi
    }

    fun getSession(): LiveData<UserModel> = repository.getSession().asLiveData()

}