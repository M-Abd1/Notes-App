package com.pucit.edu.pk.onenote

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.pucit.edu.pk.onenote.databinding.ActivitySignUpBinding


class SignUpActivity : AppCompatActivity() {

    private lateinit var dbHelper: SignUpDatabaseHelper
    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = SignUpDatabaseHelper(this)

        val nameEditText: EditText = findViewById(R.id.editTextText)
        val ageEditText: EditText = findViewById(R.id.editTextText2)
        val emailEditText: EditText = findViewById(R.id.editTextText3)
        val passwordEditText: EditText = findViewById(R.id.editTextText4)
        val signUpButton: Button = findViewById(R.id.signupButton)

        signUpButton.setOnClickListener {
            val name = nameEditText.text.toString().trim()
            val ageText = ageEditText.text.toString().trim()
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (name.isNotEmpty() && ageText.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                val age = ageText.toInt()

                val signUp = SignupModelClass(name, email, age, password) // assuming 0 as a placeholder for auto-increment ID
                dbHelper.insertSignUp(signUp)
                binding.signupButton.setOnClickListener{
                    val intent = Intent(this,MainActivity::class.java)
                    startActivity(intent)
                }

                // You may want to add additional UI or navigation logic here after successful signup
                // For example, navigating to another activity or displaying a success message.

            } else {
                // Show an error message or handle empty fields
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        dbHelper.close() // Close the database connection when the activity is destroyed
    }
}



