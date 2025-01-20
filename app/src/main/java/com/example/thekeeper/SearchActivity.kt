package com.example.thekeeper

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.thekeeper.R
import com.example.thekeeper.data.AppDatabase
import com.example.thekeeper.viewmodel.BookViewModel
import com.example.thekeeper.viewmodel.BookViewModelFactory
import com.example.thekeeper.adapter.BookAdapter

class SearchActivity : AppCompatActivity() {

    private val bookViewModel: BookViewModel by viewModels {
        BookViewModelFactory(AppDatabase.getDatabase(this).bookDao())
    }

    //activity_search
    private lateinit var adapter: BookAdapter
    private lateinit var bookRecyclerView: RecyclerView
    private lateinit var searchEditText: EditText
    private lateinit var searchButton: Button
    private lateinit var backButton: ImageButton

    //book_item

    private lateinit var addButton: Button;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        // Initialize Search_activity Components
        bookRecyclerView = findViewById(R.id.bookRecyclerView)
        searchEditText = findViewById(R.id.searchEditText)
        searchButton = findViewById(R.id.searchButton)
        backButton = findViewById(R.id.backButton)


        //item_book components



        // Set up RecyclerView
        adapter = BookAdapter(emptyList()) { book ->
            bookViewModel.addBookToLibrary(book)
            Toast.makeText(this, "${book.title} added to your library!", Toast.LENGTH_SHORT).show()
        }
        bookRecyclerView.layoutManager = LinearLayoutManager(this)
        bookRecyclerView.adapter = adapter

        // Observe LiveData for books
        bookViewModel.books.observe(this) { books ->
            if (books.isNotEmpty()) {
                adapter.updateData(books)
                bookRecyclerView.visibility = View.VISIBLE
            } else {
                Toast.makeText(this, "No books found", Toast.LENGTH_SHORT).show()
                bookRecyclerView.visibility = View.GONE
            }
        }

        // Set up Search Button //TODO reinstate later after test
        searchButton.setOnClickListener {
            val query = searchEditText.text.toString().trim()
            if (query.isNotEmpty()) {
                Log.d("SearchActivity", "Search query: $query")
                bookViewModel.searchBooks(query) // Perform the search
            } else {
                Toast.makeText(this, "Please enter a search query", Toast.LENGTH_SHORT).show()
            }
        }


        //back button will close the activity
        backButton.setOnClickListener{
            finish()
        }

    }
}
