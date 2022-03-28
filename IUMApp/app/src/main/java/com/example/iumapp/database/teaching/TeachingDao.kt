package com.example.iumapp.database.teaching

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TeachingDao {
    @Insert
    fun insert(vararg teaching: Teaching)

    @Delete
    fun delete(teaching: Teaching)

    @Query("SELECT * FROM teaching")
    fun getAll(): List<Teaching>

    @Query("DELETE FROM teaching")
    fun nukeTable();
}