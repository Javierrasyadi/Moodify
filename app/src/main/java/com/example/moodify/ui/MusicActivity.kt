package com.example.moodify.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import com.example.moodify.R
import com.example.moodify.coping.MusicFragment
import com.example.moodify.databinding.ActivityMusicBinding
import com.example.moodify.model.response.Result
import com.example.moodify.viewModel.CopingViewModel
import com.example.moodify.viewModel.SharedViewModel
import com.example.moodify.viewModel.ViewModelFactory

class MusicActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMusicBinding
    private val copingViewModel: CopingViewModel by viewModels {
        ViewModelFactory.getInstance(applicationContext)
    }
    private val sharedViewModel: SharedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMusicBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragment = MusicFragment()
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_music_container, fragment)
        fragmentTransaction.commit()

        copingViewModel.getCoping().observe(this, Observer { result ->
            when (result) {
                is Result.Loading -> {
                    // Show loading indicator
                }
                is Result.Success -> {
                    // Handle success
                    val copingResponse = result.data
                    sharedViewModel.musicUrl.value = copingResponse.recommendations?.urls?.music
                    sharedViewModel.podcastUrl.value = copingResponse.recommendations?.urls?.podcast
                }
                is Result.Error -> {
                    // Handle error
                    Log.e("CopingActivity", result.error)
                }
            }
        })
    }
}