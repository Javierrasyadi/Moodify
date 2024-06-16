package com.example.moodify.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.moodify.R
import com.example.moodify.databinding.ActivityMainBinding
import com.example.moodify.model.response.Result
import com.example.moodify.viewModel.MainViewModel
import com.example.moodify.viewModel.ViewModelFactory

class MainActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_EMAIL = "extra_email"
    }

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setView()
    }

    private fun setView() {
        binding.bottomNavBar.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.bot_nav_home -> replaceFragment(
                    HomeFragment(
                        viewModel.user,
                        viewModel.listJournal
                    )
                ) //Instance fragment with user and listjournal data
                R.id.bot_nav_journal -> replaceFragment(JournalFragment(viewModel.listJournal))//Instance fragment with listjournal data
                R.id.bot_nav_other -> replaceFragment(OtherFragment())
                else -> {
                    return@setOnItemSelectedListener false
                }
            }
        }
        replaceFragment(HomeFragment(viewModel.user, viewModel.listJournal))
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