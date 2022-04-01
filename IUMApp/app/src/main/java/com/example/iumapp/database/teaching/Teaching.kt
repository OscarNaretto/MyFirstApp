package com.example.iumapp.database.teaching

import androidx.room.Entity

@Entity(primaryKeys = ["lesson", "teacher"])
data class Teaching(
    val lesson: String,
    val teacher: String
)