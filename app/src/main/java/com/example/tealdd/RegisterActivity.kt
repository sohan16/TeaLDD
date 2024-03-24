package com.example.tealdd

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tealdd.databinding.ActivityRegisterBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Register"

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.client_id))
            .requestEmail()
            .build()

        val googleSignInClient = GoogleSignIn.getClient(this, gso)

        binding.loginTV.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        binding.createAccountBtn.setOnClickListener {
            val email = binding.emailRegister.text.toString()
            val password = binding.passwordRegister.text.toString()
            val confirmPassword = binding.confirmPasswordRegister.text.toString()
            val number = binding.phoneNumber.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty() && number.isNotEmpty()) {
                val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
                if (!email.matches(emailPattern.toRegex())) {
                    Toast.makeText(
                        this,
                        "Please use a correct email address",
                        Toast.LENGTH_LONG
                    ).show()
                    return@setOnClickListener
                }

                if (password != confirmPassword) {
                    Toast.makeText(
                        this,
                        "Passwords do not match",
                        Toast.LENGTH_LONG
                    ).show()
                    return@setOnClickListener
                }

                val phoneNumberPattern = Regex("^01[0-9]{6,10}\$")
                if (!number.matches(phoneNumberPattern)) {
                    Toast.makeText(
                        this,
                        "Please enter a valid phone number (+8801 followed by 6-10 digits)",
                        Toast.LENGTH_LONG
                    ).show()
                    return@setOnClickListener
                }

                // All fields are valid, proceed with creating the account
                MainActivity.auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            startActivity(Intent(this, LoginActivity::class.java))
                            finish()
                        }
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(this, e.localizedMessage, Toast.LENGTH_LONG).show()
                    }
            } else {
                Toast.makeText(
                    this,
                    "Please fill in all fields",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        binding.googleBtn.setOnClickListener {
            googleSignInClient.signOut()
            startActivityForResult(googleSignInClient.signInIntent, 16)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 16 && resultCode == RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val account = task.getResult(ApiException::class.java)!!
            firebaseAuthWithGoogle(account.idToken!!)
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        MainActivity.auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
            }
            .addOnFailureListener(this) { e ->
                Toast.makeText(this, e.localizedMessage, Toast.LENGTH_LONG).show()
            }
    }
}
