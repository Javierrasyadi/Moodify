package com.example.moodify.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import com.example.moodify.R
import com.example.moodify.databinding.ActivityCoping2Binding
import com.example.moodify.databinding.ActivityCopingBinding
import com.example.moodify.model.response.Result
import com.example.moodify.viewModel.CopingViewModel
import com.example.moodify.viewModel.SharedViewModel
import com.example.moodify.viewModel.ViewModelFactory

class CopingActivity2 : AppCompatActivity() {

    private lateinit var binding: ActivityCoping2Binding
    private val copingViewModel: CopingViewModel by viewModels {
        ViewModelFactory.getInstance(applicationContext)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoping2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnFinish.setOnClickListener {
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
                    binding.word2.text = copingResponse.recommendations?.textAffirmationLast
                }
                is Result.Error -> {
                    // Handle error
                    Log.e("CopingActivity", result.error)
                }
            }
        })

    }
}