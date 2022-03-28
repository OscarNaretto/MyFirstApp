package com.example.iumapp.database.reservation

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Reservation(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val lesson_id: Int,
    val teacher_id: Int,
    val user_id: Int,
    val status: Boolean,
    val day: String,
    val time_slot: Int
)