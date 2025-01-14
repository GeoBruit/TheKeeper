package com.example.thekeeper.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thekeeper.BuildConfig
import com.example.thekeeper.dao.BookDao
import com.example.thekeeper.model.Book
import com.example.thekeeper.model.GoogleBooksResponse
import com.example.thekeeper.networking.RetrofitClient
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BookViewModel(private val bookDao: BookDao) : ViewModel() {

    private val apiKey = BuildConfig.GOOGLE_BOOKS_API_KEY

    // LiveData to observe the books fetched from the API
    private val _books = MutableLiveData<List<Book>>()
    val books: LiveData<List<Book>> get() = _books

    // Fetch books from the API
    fun searchBooks(query: String) {
        RetrofitClient.api.searchBooks(query, apiKey).enqueue(object : Callback<GoogleBooksResponse> {
            override fun onResponse(
                call: Call<GoogleBooksResponse>,
                response: Response<GoogleBooksResponse>
            ) {
                Log.d("BookViewModel", "API Response: ${response.code()} - ${response.message()}")
                if (response.isSuccessful) {
                    val fetchedBooks = response.body()?.items?.map { item ->
                        Book(
                            id = item.id,
                            title = item.volumeInfo.title,
                            authors = item.volumeInfo.authors?.joinToString(", "),
                            description = item.volumeInfo.description,
                            thumbnail = item.volumeInfo.imageLinks?.thumbnail
                        )
                    } ?: emptyList()
                    Log.d("BookViewModel", "Books fetched: ${fetchedBooks.size}")
                    _books.postValue(fetchedBooks)
                } else {
                    Log.e("BookViewModel", "Failed to fetch books: ${response.errorBody()?.string()}")
                    _books.postValue(emptyList())
                }
            }

            override fun onFailure(call: Call<GoogleBooksResponse>, t: Throwable) {
                Log.e("BookViewModel", "API Call failed: ${t.message}", t)
                _books.postValue(emptyList())
            }
        })
    }

    // Add a book to the library
    fun addBookToLibrary(book: Book) {
        viewModelScope.launch {
            bookDao.insertBook(book)
        }
    }

    // Fetch books from the library
    fun getBooksFromLibrary(onResult: (List<Book>) -> Unit) {
        viewModelScope.launch {
            onResult(bookDao.getAllBooks())
        }
    }

    //Data simulation for debug
    //TODO delete later
//    fun simulateData() {
//        val mockBooks = listOf(
//            Book(
//                id = "1",
//                title = "Atomic Habits",
//                authors = "James Clear",
//                description = "An easy & proven way to build good habits & break bad ones.",
//                thumbnail = null // You can provide a valid image URL if needed
//            ),
//            Book(
//                id = "2",
//                title = "Deep Work",
//                authors = "Cal Newport",
//                description = "Rules for focused success in a distracted world.",
//                thumbnail = null
//            ),
//            Book(
//                id = "3",
//                title = "The Alchemist",
//                authors = "Paulo Coelho",
//                description = "A journey of a shepherd to find his treasure.",
//                thumbnail = null
//            )
//        )
//        _books.postValue(mockBooks) // Update LiveData with the mock data
//    }
}
