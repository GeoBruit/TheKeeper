package com.example.thekeeper

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.thekeeper.adapter.LibraryBookAdapter
import com.example.thekeeper.data.AppDatabase
import com.example.thekeeper.model.Book
import com.example.thekeeper.viewmodel.BookViewModel
import com.example.thekeeper.viewmodel.BookViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomePageActivity : AppCompatActivity() {

    private lateinit var bookRecyclerView: RecyclerView
    private lateinit var bookAdapter: LibraryBookAdapter
    private val bookViewModel: BookViewModel by viewModels {
        BookViewModelFactory(AppDatabase.getDatabase(this).bookDao())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)

        // Initialize RecyclerView
        bookRecyclerView = findViewById(R.id.bookRecyclerView)
        bookAdapter = LibraryBookAdapter { book ->
            navigateToSingleBookView(book)
        }

        // Set up GridLayoutManager with 2 columns
        bookRecyclerView.layoutManager = GridLayoutManager(this, 2)
        bookRecyclerView.adapter = bookAdapter

        // Observe library books from ViewModel
        bookViewModel.books.observe(this) { books ->
            bookAdapter.updateData(books)
        }

        // Load books from library
        bookViewModel.getBooksFromLibrary { books ->
            bookAdapter.updateData(books)
        }

        //NavBar

        //init the navbar
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        //Highlight the current item

        bottomNavigationView.selectedItemId = R.id.nav_home

        //Nav item clicks

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId){
                R.id.nav_home ->{
                    //already on home
                    true
                }
                R.id.nav_search ->{
                    startActivity(Intent(this, SearchActivity::class.java))
                    true
                }
                R.id.nav_logout ->{
                    finish()
                    true
                }
                R.id.backButton->{
                    Toast.makeText(this, "You have to logout to go back", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }
    }

    private fun navigateToSingleBookView(book: Book) {
        val intent = Intent(this, SingleBookActivity::class.java).apply {
            putExtra("bookTitle", book.title)
            putExtra("bookAuthors", book.authors)
            putExtra("bookThumbnail", book.thumbnail)
            putExtra("bookDescription", book.description)
        }
        startActivity(intent)
    }
}
