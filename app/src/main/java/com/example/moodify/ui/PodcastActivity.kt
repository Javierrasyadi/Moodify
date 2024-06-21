package com.example.moodify.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.moodify.R
import com.example.moodify.coping.PodcastFragment
import com.example.moodify.databinding.ActivityMusicBinding
import com.example.moodify.databinding.ActivityPodcastBinding
import com.example.moodify.model.response.Result
import com.example.moodify.viewModel.MusicViewModel
import com.example.moodify.viewModel.PodcastViewModel
import com.example.moodify.viewModel.SharedViewModel
import com.example.moodify.viewModel.ViewModelFactory

class PodcastActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPodcastBinding
    private val sharedViewModel: SharedViewModel by viewModels()
    private val podcastViewModel: PodcastViewModel by viewModels<PodcastViewModel> {
        ViewModelFactory.getInstance(applicationContext)
    }
    companion object{
        const val PODCAST_EXTRA = "extra_podcast"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPodcastBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragment = PodcastFragment()
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.commit()
        podcastViewModel.getPodcast().observe(this){
            when(it){
                is Result.Error -> {
                    Toast.makeText(this, "failed to retrieve data", Toast.LENGTH_SHORT).show()
                    showLoading(false)
                }
                Result.Loading -> {
                    showLoading(true)
                }
                is Result.Success -> {
                    showLoading(false)
                    val extraPodcast = intent.getStringExtra(PodcastActivity.PODCAST_EXTRA)
                    sharedViewModel.podcastUrl.value = extraPodcast
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