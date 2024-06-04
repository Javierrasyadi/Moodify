package com.example.moodify1.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.moodify.R
import com.example.moodify.databinding.ActivityProfileBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class ProfileActivity : AppCompatActivity() {
    companion object{
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_EMAIL = "extra_email"
        const val EXTRA_PASSWORD = "extra_password"
    }

    private lateinit var auth: FirebaseAuth


    private lateinit var binding: ActivityProfileBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth


        val intent = Intent(this@ProfileActivity, SignUpActivity::class.java)
        val displayName = intent.getStringExtra(EXTRA_NAME)
        val email = intent.getStringExtra(EXTRA_EMAIL).toString()
        val password = intent.getStringExtra(EXTRA_PASSWORD).toString()

        binding.tvEmail.text = auth.currentUser!!.email
        binding.tvDisplayName.text = auth.currentUser!!.displayName
        binding.imgPhotoUser

        binding.btnLogout.setOnClickListener {
            logOut()
        }
    }

    private fun logOut(){
        auth.signOut()
        val intent = Intent(this@ProfileActivity, LoginActivity::class.java)
        Toast.makeText(this, "Sign-out", Toast.LENGTH_SHORT).show()
        startActivity(intent)
        finish()
    }

}