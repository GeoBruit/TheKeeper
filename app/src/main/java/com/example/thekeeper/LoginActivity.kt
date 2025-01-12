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
import com.example.thekeeper.viewmodel.LoginViewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText //TODO Check to see if I can make it password or smth
    private lateinit var loginButton: Button
    private lateinit var backButton: Button

    //for the db
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        getComponents()
        setListeners()
    }


    fun getComponents(){
        emailInput = findViewById(R.id.emailInput)
        passwordInput = findViewById(R.id.passwordInput)
        loginButton = findViewById(R.id.loginButton)
        backButton = findViewById(R.id.backButton)
    }

    fun setListeners(){
        loginButton.setOnClickListener{
            loginUser()
        }
        backButton.setOnClickListener{
            finish() //TODO I think we can do smth else here
        }
    }

    fun loginUser(){
        val email = emailInput.text.toString().trim()
        val password = passwordInput.text.toString().trim()

        try{

            // Check for empty input
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return
            }

            // Use ViewModel to query the database
            loginViewModel.loginUser(email, password) { user ->
                if (user != null) {
                    // User found, login successful
                    Toast.makeText(this, "Welcome, ${user.name}!", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    // User not found, login failed
                    Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show()
                }
            }


        }catch (e: IllegalArgumentException){
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
        // Handle unexpected runtime errors
        Toast.makeText(this, "An unexpected error occurred: ${e.message}", Toast.LENGTH_SHORT).show()
    }
    }
}