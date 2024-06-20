package com.example.moodify.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import com.example.moodify.viewModel.LoginViewModel
import com.example.moodify.databinding.ActivityProfileBinding
import com.example.moodify.model.response.RegisterResponse
import com.example.moodify.retrofit.Preference
import com.example.moodify.retrofit.UserModel
import com.example.moodify.viewModel.MainViewModel
import com.example.moodify.viewModel.RegisterViewModel
import com.example.moodify.viewModel.ViewModelFactory

class ProfileActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_EMAIL = "extra_email"
    }
    private lateinit var binding: ActivityProfileBinding
    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
//        binding.tvDisplayName.text =
//        binding.tvEmail.text =

        supportActionBar?.hide()
        binding.btnLogout.setOnClickListener {
            viewModel.logout()
            val intent = Intent(this@ProfileActivity, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }




    }
}