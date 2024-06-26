package com.example.moodify.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import com.example.moodify.R
import com.example.moodify.coping.MusicFragment
import com.example.moodify.coping.PodcastFragment
import com.example.moodify.databinding.ActivityMusicBinding
import com.example.moodify.model.response.Result
import com.example.moodify.viewModel.CopingViewModel
import com.example.moodify.viewModel.MusicViewModel
import com.example.moodify.viewModel.PodcastViewModel
import com.example.moodify.viewModel.SharedViewModel
import com.example.moodify.viewModel.ViewModelFactory

class MusicActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMusicBinding
    private val sharedViewModel: SharedViewModel by viewModels()
    private val musicViewModel: MusicViewModel by viewModels<MusicViewModel> {
        ViewModelFactory.getInstance(applicationContext)
    }
    companion object{
        const val MUSIC_EXTRA = "extra_music"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMusicBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragment = MusicFragment()
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.commit()
        musicViewModel.getMusic().observe(this) {
            when (it){
                is Result.Loading->{
                    showLoading(true)
                }
                is Result.Success ->{
                    showLoading(false)
                    val extraMusic = intent.getStringExtra(MUSIC_EXTRA)
                    sharedViewModel.musicUrl.value = extraMusic
                }
                is Result.Error ->{
                    showLoading(false)
                    Toast.makeText(this, "failed to retrieve data", Toast.LENGTH_SHORT).show()
                    Log.e("Music", it.error)
                }
            }
        }
    }
    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.loading.visibility = View.VISIBLE
        } else {
            binding.loading.visibility = View.GONE
        }
    }
}