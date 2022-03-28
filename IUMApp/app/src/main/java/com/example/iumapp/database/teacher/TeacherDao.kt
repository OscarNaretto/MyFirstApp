package com.example.iumapp.database.teacher

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TeacherDao {
    @Insert
    fun insert(vararg teacher: Teacher)

    @Delete
    fun delete(lesson: Teacher)

    @Query("SELECT * FROM teacher")
    fun getAll(): List<Teacher>

    @Query("DELETE FROM teacher")
    fun nukeTable();
}