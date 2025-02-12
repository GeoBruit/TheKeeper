package com.example.thekeeper

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.thekeeper.SearchActivity

class MainActivity : AppCompatActivity() {

    private lateinit var registerButton: Button
    private lateinit var loginButton: Button
    private lateinit var searchButton: Button
    private lateinit var homePage : Button

    //TODO this is just for testing, will delete after
    private lateinit var seeBook : Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        getComponents()
        setupListeners()
        showWelcomeDialog()
    }

    fun getComponents(){
        // Initialize buttons
        registerButton = findViewById(R.id.registerButton)
        loginButton = findViewById(R.id.loginButton)
        seeBook = findViewById(R.id.seeBook)
        searchButton = findViewById(R.id.search)
        homePage = findViewById(R.id.homePage)

    }

    fun setupListeners() {
        // Set up button click listeners
        registerButton.setOnClickListener {
            //set up intent for the view
            val intent = Intent(this, RegisterActivity::class.java)
            //star the new activity
            startActivity(intent)
        }
        loginButton.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        seeBook.setOnClickListener{
            val intent = Intent(this, BookActivity::class.java)
            startActivity(intent)
        }

        searchButton.setOnClickListener{
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }

        homePage.setOnClickListener{
            val intent = Intent(this, HomePageActivity::class.java)
            startActivity(intent)
        }


    }

    private fun showWelcomeDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Hey user (Jimi)!")
        builder.setMessage("App is still a prototype and far from what what supposed," +
                " some buttons are here just for testing and debugging perpouses, Enjoy!")
        builder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss() // Close the dialog when "OK" is pressed
        }
        builder.setCancelable(false) // Prevent dialog dismissal by tapping outside
        builder.show()
    }

}