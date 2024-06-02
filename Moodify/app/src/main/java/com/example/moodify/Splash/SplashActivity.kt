package com.example.moodify.Splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.moodify.BoardingPage.BoardingPageActivity
import com.example.moodify.R
import com.example.moodify.LoginRegister.SignUpActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Delay for 3 seconds and then start SignUpActivity
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, BoardingPageActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000) // 3000 milliseconds = 3 seconds
    }
}