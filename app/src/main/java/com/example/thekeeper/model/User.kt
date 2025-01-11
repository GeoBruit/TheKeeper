package com.example.thekeeper.model

import android.provider.ContactsContract.CommonDataKinds.Email

class User (

    var name : String,
    var email: String,
    var password: String
)
{
    init {
        validateName(name)
        validateEmail(email)
        validatePassword(password)
    }

    private fun validateName(name: String){
        if (name.isBlank()){
            throw IllegalArgumentException("Name can t be blank")
        }
        if (name.length < 4){
            throw IllegalArgumentException("Name must be at least 4 chars")
        }
    }

    private fun validateEmail(email: String){
        if (!email.contains("@") || !email.contains("."))
            throw IllegalArgumentException("Invalid Email!")
    }

    //Validate the password and make sure it containts digits as well
    //TODO Check if there a better way to do it
    private fun validatePassword(password: String){
        if (password.length < 6 || !password.any {it.isDigit()}){
            throw IllegalArgumentException("Password must be at least 6 chars and contain digits")
        }
    }
}