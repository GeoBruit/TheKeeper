package com.example.thekeeper.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.thekeeper.data.AppDatabase
import com.example.thekeeper.model.User
import kotlinx.coroutines.launch

class RegisterViewModel(application: Application) : AndroidViewModel(application) {
    private val userDao = AppDatabase.getDatabase(application).userDao()

    fun registerUser(user: User, onComplete: () -> Unit) {
        viewModelScope.launch {
            userDao.insertUser(user)
            onComplete() // Notify that the user was registered successfully
        }
    }
}