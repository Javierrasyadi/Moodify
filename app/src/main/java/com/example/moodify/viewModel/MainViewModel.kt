package com.example.moodify.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.moodify.R
import com.example.moodify.model.response.JournalItem
import com.example.moodify.model.response.Result
import com.example.moodify.repository.MoodifyRepository
import com.example.moodify.retrofit.UserModel
import kotlinx.coroutines.launch
import org.tensorflow.lite.TensorFlowLite.init

class MainViewModel(private val repository: MoodifyRepository) : ViewModel() {
    private val _listJournal = MediatorLiveData<Result<List<JournalItem>>>()
    val listJournal: LiveData<Result<List<JournalItem>>>
        get() = _listJournal

    private val _user = MediatorLiveData<UserModel>()
    val user: LiveData<UserModel>
        get() = _user

    fun getSession() {
        val source = repository.getSession().asLiveData()
        _user.removeSource(source)
        _user.addSource(source) {
            _user.value = it
            Log.d(TAG, "getSession: ${user.value}")
        }
    }

    fun getJournal() {
        val source = repository.getJournal()
        _listJournal.removeSource(source)
        _listJournal.addSource(source) {
            _listJournal.value = it
            Log.d(TAG, "getJournal: ${listJournal.value}")
        }
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }

    companion object {
        private const val TAG = "MainViewModel"
    }
}