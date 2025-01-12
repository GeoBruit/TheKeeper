package com.example.thekeeper.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.thekeeper.data.AppDatabase
import com.example.thekeeper.model.User
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : AndroidViewModel(application){

    private val userDao = AppDatabase.getDatabase(application).userDao()

    fun loginUser(email: String, password: String, onResult: (User?) -> Unit) {
        viewModelScope.launch {
            val user = userDao.loginUser(email, password)
            onResult(user)
        }
    }
}