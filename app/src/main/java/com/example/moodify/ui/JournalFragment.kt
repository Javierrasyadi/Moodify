package com.example.moodify.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moodify.R
import com.example.moodify.adapter.JournalAdapter
import com.example.moodify.databinding.FragmentJournalBinding
import com.example.moodify.model.response.JournalItem
import com.example.moodify.model.response.Result
import com.example.moodify.viewModel.MainViewModel
import com.example.moodify.viewModel.ViewModelFactory

class JournalFragment(
    private val listJournal: LiveData<Result<List<JournalItem>>>
) : Fragment() {

    private lateinit var binding: FragmentJournalBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentJournalBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fabAdd.setOnClickListener {
            startActivity(Intent(requireActivity(), MyJournalActivity::class.java))
        }
        val adapter = JournalAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        binding.recyclerView.adapter = adapter
        listJournal.observe(viewLifecycleOwner){ result->
            if (result is Result.Success) {
                adapter.submitList(result.data)
            } else if  (result is Result.Error) {
                Toast.makeText(requireActivity(), result.error, Toast.LENGTH_SHORT).show()
            }
        }
    }
}