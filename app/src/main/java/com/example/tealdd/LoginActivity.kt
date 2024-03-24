package com.example.tealdd

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tealdd.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "LogIn"

        binding.registerTV.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }
        binding.loginBtn.setOnClickListener {
            val email = binding.emailLogin.text?.toString() ?: ""
            val password = binding.passwordLogin.text?.toString() ?: ""
            if (email.isNotEmpty() && password.isNotEmpty()) {
                signInWithEmailAndPassword(email, password)
            } else {
                Toast.makeText(this, "Please enter both email and password", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun signInWithEmailAndPassword(email: String, password: String) {
        MainActivity.auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("check", "Done")
                    startActivity(Intent(this, MainActivity::class.java))
                    finish() // Finish LoginActivity after successfully logging in
                } else {
                    Toast.makeText(this, "Authentication failed", Toast.LENGTH_LONG).show()
                }
            }
            .addOnFailureListener { exception ->
                handleAuthenticationFailure(exception)
            }
    }

    private fun handleAuthenticationFailure(exception: Exception) {
        Log.e("LoginActivity", "Authentication failed", exception) // Log the exception for debugging
        val errorMessage = when (exception) {
            is FirebaseAuthInvalidUserException -> "Invalid user"
            is FirebaseAuthInvalidCredentialsException -> "Invalid credentials"
            else -> {
                Log.e("LoginActivity", "Unexpected exception: ${exception.javaClass.simpleName}", exception)
                exception.localizedMessage ?: "Authentication failed"
            }
        }
        runOnUiThread {
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
        }
    }
}
