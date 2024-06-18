package com.example.moodify.ui

import android.content.Intent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import com.example.moodify.coping.BottomDialogFragment
import com.example.moodify.databinding.ActivityCopingBinding
import com.example.moodify.model.response.Result
import com.example.moodify.viewModel.CopingViewModel
import com.example.moodify.viewModel.SharedViewModel
import com.example.moodify.viewModel.ViewModelFactory

class CopingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCopingBinding
    private val copingViewModel: CopingViewModel by viewModels {
        ViewModelFactory.getInstance(applicationContext)
    }
    private val sharedViewModel: SharedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCopingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnStart.setOnClickListener {
            val bottomDialogFragment = BottomDialogFragment()
            bottomDialogFragment.show(supportFragmentManager, "BottomDialog")
        }

        binding.btnBackCT.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        copingViewModel.getCoping().observe(this, Observer { result ->
            when (result) {
                is Result.Loading -> {
                    // Show loading indicator
                }
                is Result.Success -> {
                    // Handle success
                    val copingResponse = result.data
                    binding.word1.text = copingResponse.recommendations?.textAffirmationFirst
                    sharedViewModel.textInstruction.value = copingResponse.recommendations?.textInstruction
                    sharedViewModel.musicUrl.value = copingResponse.recommendations?.urls?.music
                    sharedViewModel.podcastUrl.value = copingResponse.recommendations?.urls?.podcast
                }
                is Result.Error -> {
                    // Handle error
                    Log.e("CopingActivity", result.error)
                }
            }
        })
    }
}
