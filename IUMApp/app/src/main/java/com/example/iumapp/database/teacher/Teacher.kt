package com.example.iumapp.database.teacher

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Teacher (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val surname: String
)