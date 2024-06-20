package com.example.moodify.coping

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.example.moodify.R
import com.example.moodify.databinding.FragmentMusicBinding
import com.example.moodify.model.response.Result
import com.example.moodify.ui.MainActivity
import com.example.moodify.ui.OtherFragment
import com.example.moodify.viewModel.CopingViewModel
import com.example.moodify.viewModel.SharedViewModel
import com.example.moodify.viewModel.ViewModelFactory

class MusicFragment : Fragment() {
    private lateinit var playerView: PlayerView
    private lateinit var exoPlayer: ExoPlayer
    private val copingViewModel: CopingViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var binding: FragmentMusicBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMusicBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        playerView = view.findViewById(R.id.player_view_music)

        binding.fabBack.setOnClickListener {
            val intent = Intent(context, MainActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }

        exoPlayer = ExoPlayer.Builder(requireContext()).build()
        playerView.player = exoPlayer

        sharedViewModel.musicUrl.observe(viewLifecycleOwner, { url ->
            url?.let {
                val mediaItem = MediaItem.fromUri(Uri.parse(it))
                exoPlayer.setMediaItem(mediaItem)
                exoPlayer.prepare()
                exoPlayer.play()
            }
        })

        copingViewModel.getCoping().observe(requireActivity(), Observer { result ->
            when (result) {
                is Result.Loading -> {
                    // Show loading indicator
                }
                is Result.Success -> {
                    // Handle success
                    val copingResponse = result.data
                    binding.tvAffirmationText.text = copingResponse.recommendations?.textAffirmationFirst
                }
                is Result.Error -> {
                    // Handle error
                    Log.e("CopingActivity", result.error)
                }
            }
        })
    }

    private fun replaceFragment(){
        val fragmentManager = requireActivity().supportFragmentManager
        val fragmentOther = OtherFragment()
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.other_fragment, fragmentOther)
        transaction.commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        exoPlayer.release()
    }

}