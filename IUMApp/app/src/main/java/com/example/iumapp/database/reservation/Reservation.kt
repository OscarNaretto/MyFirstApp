package com.example.iumapp.database.reservation

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Reservation(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val lesson: String,
    val teacher: String,
    val user: String,
    val status: Boolean,
    val day: String,
    val time_slot: Int
)