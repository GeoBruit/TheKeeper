package com.example.thekeeper

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.thekeeper.model.User
import com.example.thekeeper.viewmodel.RegisterViewModel

class RegisterActivity : AppCompatActivity() {

    private lateinit var nameInput: EditText
    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var registerButton: Button
    private lateinit var backButton: Button

    // ViewModel instance
    private val registerViewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        getComponents()
        setListeners()
    }

    private fun getComponents() {
        nameInput = findViewById(R.id.nameInput)
        emailInput = findViewById(R.id.emailInput)
        passwordInput = findViewById(R.id.passwordInput)
        registerButton = findViewById(R.id.registerButton)
        backButton = findViewById(R.id.backButton)
    }

    private fun setListeners() {
        registerButton.setOnClickListener {
            registerUser()
        }
        backButton.setOnClickListener {
            finish()
        }
    }

    private fun registerUser() {
        val name = nameInput.text.toString().trim()
        val email = emailInput.text.toString().trim()
        val password = passwordInput.text.toString().trim()

        try {
            // Validate input fields
            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return
            }

            // Create a new user object
            val newUser = User(name = name, email = email, password = password)

            // Save the user to the database via the ViewModel instance
            registerViewModel.registerUser(newUser) {
                try {
                    // Success callback
                    Toast.makeText(this, "User registered successfully!", Toast.LENGTH_SHORT).show()
                    finish() // Close the activity and return to the previous screen
                } catch (e: Exception) {
                    // Handle unexpected errors in the success callback
                    Toast.makeText(this, "An error occurred: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        } catch (e: IllegalArgumentException) {
            // Handle validation errors
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            // Handle unexpected runtime errors
            Toast.makeText(this, "An unexpected error occurred: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
}
