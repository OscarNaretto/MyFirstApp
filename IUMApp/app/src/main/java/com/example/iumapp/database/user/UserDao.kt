package com.example.iumapp.database.user

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Insert
    fun insert(vararg user: User)

    @Delete
    fun delete(user: User)

    @Query("SELECT * FROM user")
    fun getAll(): List<User>

    @Query("DELETE FROM user")
    fun nukeTable()

    @Query("SELECT email FROM user WHERE email = :enteredEmail AND password = :enteredPassword")
    fun searchValidLoginUser(enteredEmail: String, enteredPassword: String): String
}