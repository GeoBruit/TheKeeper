package com.example.thekeeper

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.thekeeper.model.User

class RegisterActivity : AppCompatActivity() {

    private lateinit var nameInput: EditText
    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText //TODO Check to see if I can make it password or smth
    private lateinit var registerButton: Button
    private lateinit var backButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)

        getComponents()
        setListeners()

    }



    fun getComponents(){
        nameInput = findViewById(R.id.nameInput)
        emailInput = findViewById(R.id.emailInput)
        passwordInput = findViewById(R.id.passwordInput)
        registerButton = findViewById(R.id.registerButton)
        backButton = findViewById(R.id.backButton)
    }

    fun setListeners(){
        registerButton.setOnClickListener{
            registerUser()
        }
        backButton.setOnClickListener{
            finish() //TODO I think we can do smth else here
        }
    }

    fun registerUser(){
        val name = nameInput.text.toString().trim()
        val email = emailInput.text.toString().trim()
        val password = passwordInput.toString().trim()

        try{
            //Create a new user Obj
            //TODO might change to UserDTO is we get the db working
            val theUser = User(name, email, password)

            //TODO Check about toast
            Toast.makeText(this, "User Registered Successfully", Toast.LENGTH_SHORT).show()
            finish() // close the activyty and return
        }catch (e: IllegalArgumentException){
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
        }
    }
}