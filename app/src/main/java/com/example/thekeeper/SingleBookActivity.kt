package com.example.thekeeper

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.thekeeper.data.AppDatabase
import com.google.android.material.bottomnavigation.BottomNavigationView

class SingleBookActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_book)

        val bookTitleTextView: TextView = findViewById(R.id.bookTitle)
        val bookAuthorsTextView: TextView = findViewById(R.id.bookAuthors)
        val bookDescriptionTextView: TextView = findViewById(R.id.bookDescription)
        val bookThumbnailImageView: ImageView = findViewById(R.id.bookThumbnail)

        // Retrieve book data from Intent
        val bookTitle = intent.getStringExtra("bookTitle") ?: "No Title"
        val bookAuthors = intent.getStringExtra("bookAuthors") ?: "Unknown Authors"
        val bookDescription = intent.getStringExtra("bookDescription") ?: "No Description"
        val bookThumbnail = intent.getStringExtra("bookThumbnail")

        // Set data to UI components
        bookTitleTextView.text = bookTitle
        bookAuthorsTextView.text = bookAuthors
        bookDescriptionTextView.text = bookDescription

        // Load thumbnail using Glide
        if (bookThumbnail != null) {
            Glide.with(this)
                .load(bookThumbnail)
                .placeholder(R.drawable.placeholder_book)
                .into(bookThumbnailImageView)
        } else {
            bookThumbnailImageView.setImageResource(R.drawable.placeholder_book)
        }

        //NavBar


        //init the navbar
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        //Highlight the current item

        bottomNavigationView.selectedItemId = 0
        //Nav item clicks

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId){
                R.id.nav_home ->{
                    //already on home
                    startActivity(Intent(this, HomePageActivity::class.java))
                    true
                }
                R.id.nav_search ->{
                    startActivity(Intent(this, SearchActivity::class.java))
                    true
                }
                R.id.nav_logout ->{
                    startActivity(Intent(this, MainActivity::class.java))
                    true
                }
                R.id.backButton->{
                    startActivity(Intent(this, HomePageActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }


    }

