package com.example.iumapp.database.lesson

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Lesson(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String
) {
    override fun toString(): String {
        return name
    }
}