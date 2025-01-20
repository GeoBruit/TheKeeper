package com.example.thekeeper

import android.icu.text.CaseMap.Title
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class BookActivity : AppCompatActivity() {

    private lateinit var author : TextView
    private lateinit var title: TextView
    private lateinit var description : TextView
    private lateinit var publishedDate : TextView //TODO might change to date type after
    private lateinit var backButton : ImageButton
    private lateinit var addNoteButton: Button
    private lateinit var seeNotesButton: Button
    private lateinit var coverPage: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_book)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        getComponents()
        setListeners()
    }



    private fun getComponents() {

        author = findViewById(R.id.author)
        title = findViewById(R.id.title)
        description = findViewById(R.id.description)
        publishedDate = findViewById(R.id.publishDate)
        backButton = findViewById(R.id.backButton)
        addNoteButton = findViewById(R.id.addNotesButton)
        seeNotesButton = findViewById(R.id.seeNotesButton)
        coverPage = findViewById(R.id.coverPage)

    }

    private fun setListeners() {

        backButton.setOnClickListener{
            finish()
        }
    }


}