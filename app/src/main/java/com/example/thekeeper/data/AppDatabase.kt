package com.example.thekeeper.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.thekeeper.dao.BookDao
import com.example.thekeeper.dao.UserDao
import com.example.thekeeper.model.Book
import com.example.thekeeper.model.User

@Database(entities = [User::class, Book::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun bookDao(): BookDao // Add the BookDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )
                    .fallbackToDestructiveMigration() // Handle schema updates (e.g., adding Book)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}