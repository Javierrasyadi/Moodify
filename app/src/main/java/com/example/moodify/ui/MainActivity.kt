package com.example.moodify.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.MediatorLiveData
import com.example.moodify.Classifier
import com.example.moodify.R
import com.example.moodify.databinding.ActivityMainBinding
import com.example.moodify.model.response.Mood
import com.example.moodify.model.response.Result
import com.example.moodify.viewModel.MainViewModel
import com.example.moodify.viewModel.ViewModelFactory
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_MOOD_DESC = "extra_mood_desc"
    }
    private lateinit var classifier: Classifier
    private lateinit var homeFragment: HomeFragment
    private lateinit var journalFragment: JournalFragment
    private lateinit var otherFragment: OtherFragment
    private val mood = MediatorLiveData<String>()

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        classifier = Classifier(this)
        classifier.load("model.tflite", "word_dict.json") {
            initFragment()
            setView()
        }
    }

    private fun setView() {
        binding.bottomNavBar.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.bot_nav_home -> replaceFragment(homeFragment) //Instance fragment with user and listjournal data
                R.id.bot_nav_journal -> replaceFragment(journalFragment)//Instance fragment with listjournal data
                R.id.bot_nav_other -> replaceFragment(otherFragment)
                else -> {
                    return@setOnItemSelectedListener false
                }
            }
        }
        replaceFragment(homeFragment)
    }

    private fun initFragment(){
        mood.addSource(viewModel.listJournal){result ->
            if (result is Result.Success){
                val journal = result.data.first()
                runBlocking {
                    val predictedMood = async { classifier.classify(journal.journalText) }
                    mood.value = predictedMood.await()
                }
            }

        }
        homeFragment = HomeFragment(viewModel.user, viewModel.listJournal, mood)
        journalFragment = JournalFragment(viewModel.listJournal)
        otherFragment = OtherFragment()
    }

    private fun replaceFragment(fragment: Fragment): Boolean {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout_main, fragment)
        fragmentTransaction.commit()
        return true
    }

    override fun onResume() {
        super.onResume()
        viewModel.getJournal()
        viewModel.getSession()
    }
}