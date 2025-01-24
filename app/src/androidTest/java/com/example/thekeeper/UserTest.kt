package com.example.thekeeper


import androidx.lifecycle.ViewModel
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

    @Test
    fun checkUserIsDeleted() = runBlocking {
        // Insert a user
        val user = User(30, "Boby", "bob@test.com", "password123")
        userDao.insertUser(user)

        // Delete the user
        userDao.deleteUser(user)

        // Try retrieving the deleted user
        val retrievedUser = userDao.loginUser("bob@test.com", "password123")

        // Verify the user no longer exists
        assertEquals(null, retrievedUser)
    }


    @Test
    fun checkLoginFailsWithInvalidCredentials() = runBlocking {
        // Insert a user
        val user = User(50, "Emma", "emma@test.com", "password123")
        userDao.insertUser(user)

        // Try logging in with incorrect credentials
        val retrievedUser = userDao.loginUser("emma@test.com", "wrongpassword")

        // Verify the user is not retrieved
        assertEquals(null, retrievedUser)
    }

    @Test
    fun checkUserIsUpdated() = runBlocking {
        // Insert a user
        val user = User(20, "Alice", "alice@test.com", "password123")
        userDao.insertUser(user)

        // Update the user's details
        val updatedUser = User(20, "Alice Updated", "alice@test.com", "newpassword456")
        userDao.updateUser(updatedUser)

        // Retrieve the updated user
        val retrievedUser = userDao.loginUser("alice@test.com", "newpassword456")

        // Verify the updates
        assertEquals("Alice Updated", retrievedUser?.name)
        assertEquals("alice@test.com", retrievedUser?.email)
    }


}