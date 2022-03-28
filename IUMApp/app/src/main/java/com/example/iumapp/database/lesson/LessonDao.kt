package com.example.iumapp.database.lesson

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface LessonDao {
    @Insert
    fun insert(vararg lesson: Lesson)

    @Delete
    fun delete(lesson: Lesson)

    @Query("SELECT * FROM lesson")
    fun getAll(): List<Lesson>

    @Query("DELETE FROM lesson")
    fun nukeTable();
}