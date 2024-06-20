package com.example.moodify.ui

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.example.moodify.Classifier
import com.example.moodify.R
import com.example.moodify.databinding.ActivityMyJournalBinding
import com.example.moodify.ml.Model
import com.example.moodify.viewModel.MyJournalViewModel
import com.example.moodify.viewModel.ViewModelFactory
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.nio.ByteBuffer
import com.example.moodify.model.response.Result
import com.example.moodify.ui.MoodActivity.Companion.EXTRA_MOOD
import com.example.moodify.viewModel.MainViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking
import java.nio.ByteOrder

class MyJournalActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMyJournalBinding
    private val viewModel by viewModels<MyJournalViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var classifier: Classifier

    private val isNew by lazy { intent.getBooleanExtra(EXTRA_IS_NEW, false) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyJournalBinding.inflate(layoutInflater)
        setContentView(binding.root)
        classifier = Classifier(this)
        classifier.load("model.tflite", "word_dict.json") {
            binding.submitBtn.isEnabled = true
        }

        binding.submitBtn.setOnClickListener {
            val description = binding.etJournal.text.toString().trim()
            binding.submitBtn.isEnabled = description.length >= 10
            runBlocking {
                val predictedMood = async { classifier.classify(description) }
                Log.d(TAG, "Description: $description")
                Log.d(TAG, "Emotion prediction: ${predictedMood.await()}")
                submitJournal(description, predictedMood.await())
            }
        }
    }

    private fun submitJournal(description: String, mood: String) {
        viewModel.addJournal("My Journal", description, mood).observe(this) { result ->
            when (result) {
                is Result.Success -> {
                    binding.progressIndicator.visibility = View.GONE
                    if (result != null) {
                        val intent = Intent(this@MyJournalActivity, MoodActivity::class.java)
                        intent.putExtra(EXTRA_MOOD, mood)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(applicationContext, result.data, Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }

                is Result.Loading -> {
                    binding.progressIndicator.visibility = View.VISIBLE
                }

                is Result.Error -> {
                    binding.progressIndicator.visibility = View.GONE
                    Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    companion object {
        private const val TAG = "MyJournalActivity"
        const val EXTRA_IS_NEW = "extra_is_new"
    }
}