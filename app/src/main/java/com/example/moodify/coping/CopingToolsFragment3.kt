// CopingToolsFragment3.kt
package com.example.moodify.coping

import android.content.Intent
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
import com.example.moodify.ui.CopingActivity2
import com.example.moodify.viewModel.SharedViewModel

class CopingToolsFragment3 : Fragment() {

    private lateinit var playerView: PlayerView
    private lateinit var exoPlayer: ExoPlayer
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_copingtools_3, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        playerView = view.findViewById(R.id.player_view)

        val nextTextView = view.findViewById<TextView>(R.id.next)
        nextTextView.setOnClickListener {
            val intent = Intent(activity, CopingActivity2::class.java)
            startActivity(intent)
            activity?.finish()
        }

        val prevTextView = view.findViewById<TextView>(R.id.prev)
        prevTextView.setOnClickListener {
            navigateToFragment(CopingToolsFragment2(), "left")
        }

        // Initialize ExoPlayer
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
    }

    private fun navigateToFragment(fragment: Fragment, direction: String) {
        val transaction = parentFragmentManager.beginTransaction()

        when (direction) {
            "left" -> transaction.setCustomAnimations(
                R.anim.slide_in_left,
                R.anim.slide_out_right,
                R.anim.slide_in_right,
                R.anim.slide_out_left
            )
            "right" -> transaction.setCustomAnimations(
                R.anim.slide_in_right,
                R.anim.slide_out_left,
                R.anim.slide_in_left,
                R.anim.slide_out_right
            )
        }

        transaction.replace(R.id.bottom_sheet_fragment_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        exoPlayer.release()
    }
}


