package com.example.moodify.coping

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.example.moodify.R
import com.example.moodify.databinding.FragmentMusicBinding
import com.example.moodify.databinding.FragmentPodcastBinding
import com.example.moodify.model.response.Result
import com.example.moodify.ui.MainActivity
import com.example.moodify.viewModel.CopingViewModel
import com.example.moodify.viewModel.PodcastViewModel
import com.example.moodify.viewModel.SharedViewModel
import com.example.moodify.viewModel.ViewModelFactory

class PodcastFragment : Fragment() {
    private lateinit var playerView: PlayerView
    private lateinit var exoPlayer: ExoPlayer
    private val copingViewModel: CopingViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var binding: FragmentPodcastBinding
    private val podcastViewModel: PodcastViewModel by viewModels<PodcastViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
       binding = FragmentPodcastBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        playerView = view.findViewById(R.id.player_view_podcast)

        binding.fabBack.setOnClickListener {
            val intent = Intent(context, MainActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
        exoPlayer = ExoPlayer.Builder(requireContext()).build()
        playerView.player = exoPlayer

        sharedViewModel.podcastUrl.observe(viewLifecycleOwner, { url ->
            url?.let {
                val mediaItem = MediaItem.fromUri(Uri.parse(it))
                exoPlayer.setMediaItem(mediaItem)
                exoPlayer.prepare()
                exoPlayer.play()
            }
        })

        podcastViewModel.getPodcast().observe(requireActivity(), Observer { result ->
            when (result) {
                is Result.Loading -> {
                    showLoading(true)
                }
                is Result.Success -> {
                    // Handle success
                    showLoading(false)
                    val podcastWord = result.data
                    binding.tvAffirmationText.text = podcastWord.recommendationsPodcast.joy.textAffirmationFirst.random()
                }
                is Result.Error -> {
                    // Handle error
                    showLoading(false)
                    Toast.makeText(context, "Failed to retrieve data", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
    override fun onDestroyView() {
        super.onDestroyView()
        exoPlayer.release()
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.loading.visibility = View.VISIBLE
        } else {
            binding.loading.visibility = View.GONE
        }
    }

}