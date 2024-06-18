package com.example.moodify.coping

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.moodify.R
import com.example.moodify.databinding.FragmentCopingtools1Binding
import com.example.moodify.viewModel.SharedViewModel

class CopingToolsFragment1 : Fragment() {

    private lateinit var binding: FragmentCopingtools1Binding
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCopingtools1Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val nextTextView = view.findViewById<TextView>(R.id.next)
        nextTextView.setOnClickListener {
            (parentFragment as? BottomDialogFragment)?.navigateToFragment(CopingToolsFragment2(), "right")
        }

        sharedViewModel.textInstruction.observe(viewLifecycleOwner, Observer { textInstruction ->
            binding.tvInstruction.text = textInstruction
        })
    }
}