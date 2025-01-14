package com.example.thekeeper.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.thekeeper.R
import com.example.thekeeper.model.Book

class LibraryBookAdapter(
    private val onBookClicked: (Book) -> Unit
) : RecyclerView.Adapter<LibraryBookAdapter.BookViewHolder>() {

    private var books: List<Book> = emptyList()

    class BookViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val thumbnail: ImageView = view.findViewById(R.id.bookThumbnail)
        val title: TextView = view.findViewById(R.id.bookTitle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_library_book, parent, false)
        return BookViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = books[position]
        holder.title.text = book.title ?: "No Title Available"

        Glide.with(holder.thumbnail.context)
            .load(book.thumbnail)
            .placeholder(R.drawable.placeholder_book)
            .into(holder.thumbnail)

        holder.itemView.setOnClickListener {
            onBookClicked(book)
        }
    }

    override fun getItemCount(): Int = books.size

    fun updateData(newBooks: List<Book>) {
        books = newBooks
        notifyDataSetChanged()
    }
}