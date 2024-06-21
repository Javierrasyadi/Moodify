package com.example.moodify.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.moodify.viewModel.RegisterViewModel
import com.example.moodify.databinding.ActivitySignUpBinding
import com.example.moodify.model.response.Result
import com.example.moodify.viewModel.ViewModelFactory

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private val registerViewModel: RegisterViewModel by viewModels{
        ViewModelFactory.getInstance(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        registerViewModel.getSession().observe(this) {
            if (it.isLogin) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }

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
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnCreate.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val name = binding.etFullname.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            if (email.isNotEmpty() && name.isNotEmpty() && password.isNotEmpty()) {
                showLoading(true)
                registerViewModel.signup(name, email, password).observe(this@RegisterActivity) {
                    when (it) {
                        is Result.Loading -> {
                            showLoading(true)
                        }

                        is Result.Error -> {
                            Toast.makeText(this@RegisterActivity, "Please input fields", Toast.LENGTH_SHORT).show()
                            showLoading(false)
                        }

                        is Result.Success -> {
                            Toast.makeText(this@RegisterActivity, it.data, Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                            intent.putExtra(LoginActivity.EXTRA_NAME, name)
                            intent.putExtra(LoginActivity.EXTRA_EMAIL, email)
                            startActivity(intent)
                        }
                    }
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean){
        if (isLoading){
            binding.loading.visibility = View.VISIBLE
        }else{
            binding.loading.visibility = View.GONE
        }
    }
}
