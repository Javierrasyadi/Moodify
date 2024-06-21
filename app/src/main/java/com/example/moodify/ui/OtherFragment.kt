package com.example.moodify.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.moodify.R
import com.example.moodify.coping.MusicFragment
import com.example.moodify.coping.PodcastFragment
import com.example.moodify.databinding.FragmentHomeBinding
import com.example.moodify.databinding.FragmentOtherBinding
import com.example.moodify.model.response.MusicCategoryResponse
import com.example.moodify.model.response.Result
import com.example.moodify.viewModel.MusicViewModel
import com.example.moodify.viewModel.PodcastViewModel
import com.example.moodify.viewModel.SharedViewModel
import com.example.moodify.viewModel.ViewModelFactory

class OtherFragment : Fragment() {

    private lateinit var binding: FragmentOtherBinding
    private val musicViewModel: MusicViewModel by viewModels<MusicViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    private val podcastViewModel: PodcastViewModel by viewModels<PodcastViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentOtherBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.cvMusic1.setOnClickListener {
            musicViewModel.getMusic().observe(viewLifecycleOwner, Observer {
                when(it){
                    is Result.Error -> {
                        showLoading(false)
                    }
                    Result.Loading -> {
                        showLoading(true)
                    }
                    is Result.Success ->{
                        showLoading(false)
                        val music1 = it.data
                        val sharedMusic = music1.recommendationsMusic.anger.music[0]
                        val intent = Intent(requireActivity(), MusicActivity::class.java)
                        intent.putExtra(MusicActivity.MUSIC_EXTRA, sharedMusic)
                        startActivity(intent)
                    }
                }
            })
        }

        binding.cvMusic2.setOnClickListener {
            musicViewModel.getMusic().observe(viewLifecycleOwner, Observer {
                when(it){
                    is Result.Error -> {
                        it.error
                    }
                    Result.Loading -> {

                    }
                    is Result.Success ->{
                        val music1 = it.data
                        val sharedMusic = music1.recommendationsMusic.happy.music[0]
                        val intent = Intent(requireActivity(), MusicActivity::class.java)
                        intent.putExtra(MusicActivity.MUSIC_EXTRA, sharedMusic)
                        startActivity(intent)
                    }
                }
            })
        }

        binding.cvMusic3.setOnClickListener {
            musicViewModel.getMusic().observe(viewLifecycleOwner, Observer {
                when(it){
                    is Result.Error -> {
                        it.error
                    }
                    Result.Loading -> {

                    }
                    is Result.Success ->{
                        val music1 = it.data
                        val sharedMusic = music1.recommendationsMusic.love.music[0]
                        val intent = Intent(requireActivity(), MusicActivity::class.java)
                        intent.putExtra(MusicActivity.MUSIC_EXTRA, sharedMusic)
                        startActivity(intent)
                    }
                }
            })
        }

        binding.cvMusic4.setOnClickListener {
            musicViewModel.getMusic().observe(viewLifecycleOwner, Observer {
                when(it){
                    is Result.Error -> {
                        it.error
                    }
                    Result.Loading -> {

                    }
                    is Result.Success ->{
                        val music1 = it.data
                        val sharedMusic = music1.recommendationsMusic.sadness.music[0]
                        val intent = Intent(requireActivity(), MusicActivity::class.java)
                        intent.putExtra(MusicActivity.MUSIC_EXTRA, sharedMusic)
                        startActivity(intent)
                    }
                }
            })
        }

        binding.cvPodcast1.setOnClickListener {
            podcastViewModel.getPodcast().observe(viewLifecycleOwner, Observer {
                when(it){
                    is Result.Error -> {
                        it.error
                    }
                    Result.Loading -> {

                    }
                    is Result.Success ->{
                        val podcast1 = it.data
                        val sharedPodcast = podcast1.recommendationsPodcast.sadness.podcast[0]
                        val intent = Intent(requireActivity(), PodcastActivity::class.java)
                        intent.putExtra(PodcastActivity.PODCAST_EXTRA, sharedPodcast)
                        startActivity(intent)
                    }
                }
            })
        }

        binding.cvPodcast2.setOnClickListener {
            podcastViewModel.getPodcast().observe(viewLifecycleOwner, Observer {
                when(it){
                    is Result.Error -> {
                        it.error
                    }
                    Result.Loading -> {

                    }
                    is Result.Success ->{
                        val podcast1 = it.data
                        val sharedPodcast = podcast1.recommendationsPodcast.happy.podcast[0]
                        val intent = Intent(requireActivity(), PodcastActivity::class.java)
                        intent.putExtra(PodcastActivity.PODCAST_EXTRA, sharedPodcast)
                        startActivity(intent)
                    }
                }
            })
        }

        binding.cvPodcast3.setOnClickListener {
            podcastViewModel.getPodcast().observe(viewLifecycleOwner, Observer {
                when(it){
                    is Result.Error -> {
                        it.error
                    }
                    Result.Loading -> {

                    }
                    is Result.Success ->{
                        val podcast1 = it.data
                        val sharedPodcast = podcast1.recommendationsPodcast.love.podcast[0]
                        val intent = Intent(requireActivity(), PodcastActivity::class.java)
                        intent.putExtra(PodcastActivity.PODCAST_EXTRA, sharedPodcast)
                        startActivity(intent)
                    }
                }
            })
        }

        binding.cvPodcast4.setOnClickListener {
            podcastViewModel.getPodcast().observe(viewLifecycleOwner, Observer {
                when(it){
                    is Result.Error -> {
                        it.error
                        showLoading(false)
                    }
                    Result.Loading -> {
                        showLoading(true)
                    }
                    is Result.Success ->{
                        showLoading(false)
                        val podcast1 = it.data
                        val sharedPodcast = podcast1.recommendationsPodcast.anger.podcast[0]
                        val intent = Intent(requireActivity(), PodcastActivity::class.java)
                        intent.putExtra(PodcastActivity.PODCAST_EXTRA, sharedPodcast)
                        startActivity(intent)
                    }
                }
            })
        }

        binding.cvMeditation.setOnClickListener {
            val intent = Intent(requireActivity(),CopingActivity::class.java)
            startActivity(intent)
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