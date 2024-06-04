package com.example.moodify1.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import com.example.moodify.R
import com.example.moodify.databinding.ActivitySignUpBinding
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth

class SignUpActivity : AppCompatActivity() {
    companion object{
        private const val REQUEST_CODE_SIGN_IN = 10001
    }

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvClickHere.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        auth = Firebase.auth

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("643160831565-9q86k9e9metjf16r2t6266kela9ac720.apps.googleusercontent.com")
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        binding.btnCreate.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val name = binding.etFullname.text.toString()
            val password = binding.etPassword.text.toString()

            if (checkField()){
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (it.isSuccessful){
                        val intent = Intent(this@SignUpActivity, LoginActivity::class.java)
                        intent.putExtra(ProfileActivity.EXTRA_NAME, name)
                        intent.putExtra(ProfileActivity.EXTRA_EMAIL, email)
                        intent.putExtra(ProfileActivity.EXTRA_PASSWORD, password)
                        auth.signOut()
                        Toast.makeText(this, "Account created successfully", Toast.LENGTH_SHORT).show()
                        startActivity(intent)
                        finish()
                    } else{
                        Log.e("error: ", it.exception.toString())
                    }
                }
            }
        }

        binding.btnGoogle.setOnClickListener {
            signInWithGoogle()
        }
    }

    private fun checkField(): Boolean{
        val email = binding.etEmail.text.toString()
        val name = binding.etFullname.text.toString()
        val password = binding.etPassword.text.toString()
        if (name == ""){
            binding.textInputLayoutName.error = "Required field"
            return false
        }

        if(email == ""){
            binding.textInputLayoutEmail.error = "Required field"
            return false
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.textInputLayoutEmail.error = "invalid email format"
            return false
        }
        if (password.length <= 8){
            binding.textInputLayoutPassword.error = "the password should at least 8 characters long"
            return false
        }
        return true
    }

    override fun onStart() {
        super.onStart()
        if (auth.currentUser != null){
            val intent = Intent(this@SignUpActivity, ProfileActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun signInWithGoogle(){
        val signInIntent = googleSignInClient.signInIntent
        launcher.launch(signInIntent)
    }

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if(it.resultCode == Activity.RESULT_OK){
            val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            handleResult(task)
        }
    }

    private fun handleResult(task: Task<GoogleSignInAccount>){
        if(task.isSuccessful){
            val account: GoogleSignInAccount? = task.result
            if (account != null){
                updateUI(account)
            }else{
                Toast.makeText(this, "fail to login", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateUI(account: GoogleSignInAccount){
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener{
            if (it.isSuccessful){
                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@SignUpActivity, ProfileActivity::class.java)
                startActivity(intent)
            }else{
                Log.e("error: ",it.exception.toString())
            }
        }
    }
}
