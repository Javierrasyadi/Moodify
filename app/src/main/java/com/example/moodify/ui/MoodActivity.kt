package com.example.moodify.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.moodify.R
import com.example.moodify.databinding.ActivityLoginBinding
import com.example.moodify.databinding.ActivityMoodBinding

class MoodActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMoodBinding
    private val mood by lazy { intent.getStringExtra(EXTRA_MOOD) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMoodBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setFullScreen()
        setView()
        setListener()
    }

    private fun setFullScreen() {
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setView() {
        binding.apply {
            when (mood) {
                "anger", "fear", "sadness" -> {
                    ivMood.setImageResource(R.drawable.ic_mood_sad)
                    tvMood.text = "Sad"
                }

                "love", "joy", "happy" -> {
                    ivMood.setImageResource(R.drawable.ic_mood_happy)
                    tvMood.text = "Happy"
                }

                else -> {
                    ivMood.setImageResource(R.drawable.ic_mood_neutral)
                    tvMood.text = "Neutral"
                }
            }
        }
    }

    private fun setListener() {
        binding.btnMedication.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    companion object {
        const val EXTRA_MOOD = "extra_mood"
    }
}