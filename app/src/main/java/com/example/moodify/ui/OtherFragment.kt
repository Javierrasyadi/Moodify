package com.example.moodify.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.moodify.R
import com.example.moodify.coping.MusicFragment
import com.example.moodify.databinding.FragmentHomeBinding
import com.example.moodify.databinding.FragmentOtherBinding

class OtherFragment : Fragment() {

    private lateinit var binding: FragmentOtherBinding

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
            val intent = Intent(requireActivity(), MusicActivity::class.java)
            startActivity(intent)
        }

        binding.cvPodcast1.setOnClickListener {
            val intent = Intent(requireActivity(), MusicActivity::class.java)
            startActivity(intent)
        }
    }

}