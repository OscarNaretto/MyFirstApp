package com.example.iumapp.database.teaching

import androidx.room.Entity

@Entity(primaryKeys = ["lesson_id", "teacher_id"])
data class Teaching(
    val lesson_id: Int,
    val teacher_id: Int
)