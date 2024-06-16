package com.example.moodify.injection

import android.content.Context
import android.util.Log
import com.example.moodify.repository.MoodifyRepository
import com.example.moodify.retrofit.ApiConfig
import com.example.moodify.retrofit.Preference
import com.example.moodify.retrofit.dataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideRepository(context: Context): MoodifyRepository {
        val pref = Preference.getInstance(context.dataStore)
        val session = runBlocking { pref.getSession().first() }
        Log.d("Injection", "provideRepository: $session")
        val apiService = ApiConfig.getApiService(session.token)
        return MoodifyRepository.getInstance(apiService, pref)
    }

}