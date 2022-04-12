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

    @Query("SELECT name FROM lesson")
    fun getAll(): List<String>

    @Query("SELECT description FROM lesson WHERE name = :lessonName")
    fun getDescription(lessonName: String): String

    @Query("DELETE FROM lesson")
    fun nukeTable()
}