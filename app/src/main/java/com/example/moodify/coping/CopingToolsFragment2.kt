package com.example.moodify.coping

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.example.moodify.R
import com.example.moodify.viewModel.SharedViewModel

class CopingToolsFragment2 : Fragment() {

    private lateinit var playerView: PlayerView
    private lateinit var exoPlayer: ExoPlayer
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_copingtools_2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        playerView = view.findViewById(R.id.player_view)

        val nextTextView = view.findViewById<TextView>(R.id.next)
        nextTextView.setOnClickListener {
            (parentFragment as? BottomDialogFragment)?.navigateToFragment(CopingToolsFragment3(), "right")
        }

        val prevTextView = view.findViewById<TextView>(R.id.prev)
        prevTextView.setOnClickListener {
            (parentFragment as? BottomDialogFragment)?.navigateToFragment(CopingToolsFragment1(), "left")
        }


        // Initialize ExoPlayer
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        exoPlayer.release()
    }
}




