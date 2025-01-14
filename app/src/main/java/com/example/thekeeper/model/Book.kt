package com.example.thekeeper.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
data class Book(
    @PrimaryKey val id: String, //Will have the same ID like the one in the google db
    val title: String,
    val authors: String?, // Comma-separated list of authors
    val description: String?,
    val thumbnail: String?
)