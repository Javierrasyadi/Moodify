package com.example.moodify.ui

import android.content.Intent
import android.os.Build
import android.os.Build.VERSION_CODES.M
import android.os.Bundle
import android.provider.ContactsContract.Profile
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.moodify.viewModel.LoginViewModel
import com.example.moodify.databinding.ActivityLoginBinding
import com.example.moodify.model.response.Result
import com.example.moodify.ui.MyJournalActivity.Companion.EXTRA_IS_NEW
import com.example.moodify.viewModel.ViewModelFactory

class LoginActivity : AppCompatActivity() {
    companion object {
         const val EXTRA_NAME = "extra_name"
        const val EXTRA_EMAIL = "extra_email"
    }

    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()

        binding.tvClickHere.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            loginViewModel.login(email, password).observe(this@LoginActivity) {
                when (it) {
                    is Result.Loading -> {
                        showLoading(true)
                    }

                    is Result.Success -> {
                        val getName = intent.getStringExtra(EXTRA_NAME)
                        Log.d("loginSucces", it.data)
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        intent.putExtra(ProfileActivity.EXTRA_NAME, getName)
                        intent.putExtra(ProfileActivity.EXTRA_EMAIL, email)
                        startActivity(intent)
                        finish()
                    }

                    is Result.Error -> {
                        Log.d("loginerror", it.error)
                        Toast.makeText(this, "Please input correct email/password", Toast.LENGTH_SHORT).show()
                        showLoading(false)
                    }
                }
            }
        }

    }


    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.loading.visibility = View.VISIBLE
        } else {
            binding.loading.visibility = View.GONE
        }
    }
}
