package com.example.moodify.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    val textInstruction: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val musicUrl: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val podcastUrl: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
}