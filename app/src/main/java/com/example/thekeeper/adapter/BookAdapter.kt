package com.example.thekeeper.adapter


import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.thekeeper.R
import com.example.thekeeper.model.Book

class BookAdapter(
    private var books: List<Book>,
    private val onAddToLibraryClicked: (Book) -> Unit
) : RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    class BookViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.titleTextView) // Matches item_book.xml
        val authors: TextView = view.findViewById(R.id.authorsTextView) // Matches item_book.xml
        val thumbnail: ImageView = view.findViewById(R.id.thumbnailImageView) // Matches item_book.xml
        val addButton: TextView = view.findViewById(R.id.addButton) // Matches item_book.xml
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_book, parent, false)
        return BookViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = books[position]

        // Set title
        holder.title.text = book.title ?: "No Title Available"

        // Set authors
        holder.authors.text = book.authors ?: "Unknown Author"

        // Set thumbnail using Glide
        Glide.with(holder.thumbnail.context)
            .load(book.thumbnail)
            .placeholder(R.drawable.placeholder_book) // Placeholder image
            .into(holder.thumbnail)

        // Set add button click listener
        holder.addButton.setOnClickListener {
            onAddToLibraryClicked(book)
        }
    }


    override fun getItemCount(): Int = books.size

    fun updateData(newBooks: List<Book>) {
        books = newBooks
        Log.d("BookAdapter", "Adapter updated with ${newBooks.size} books")
        notifyDataSetChanged()
    }
}
