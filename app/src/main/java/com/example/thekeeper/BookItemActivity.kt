package com.example.thekeeper

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class BookItemActivity : AppCompatActivity() {

    private lateinit var addButton :Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(R.layout.item_book)

        getComponents()
        setListeners()
    }

    private fun setListeners() {

        addButton.setOnClickListener{

            Toast.makeText(this, "Book Added successfully", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getComponents() {

        addButton = findViewById(R.id.addButton)
    }
}