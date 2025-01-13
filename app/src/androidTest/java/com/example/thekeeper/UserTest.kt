package com.example.thekeeper


import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.thekeeper.dao.UserDao
import com.example.thekeeper.data.AppDatabase
import com.example.thekeeper.model.User
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test


class UserTest {

    //Create instance of db and userDao
    private lateinit var database: AppDatabase
    private lateinit var userDao: UserDao

    //Initial setup

    @Before
    fun setup(){
        //create in memory db for testing
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries()
            .build()

        userDao = database.userDao()
    }


    @After
    fun teardown(){
        //close db after each test
        database.close()
    }

    @Test
    fun checkUserIsInsertedIntoDb() = runBlocking{ //runBlocking let's us run async functions

        //Create a new user
        val user = User(10,"Johny", "johny@test.com", "password123")

        //Insert user into db
        userDao.insertUser(user)

        //Verify results
        val retrievedUser = userDao.loginUser("johny@test.com", "password123")

        assertEquals("Johny", retrievedUser?.name)
        assertEquals("johny@test.com", retrievedUser?.email)
    }
}