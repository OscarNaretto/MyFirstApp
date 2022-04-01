package com.example.iumapp.database.teacher

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Teacher (
    @PrimaryKey val name_surname: String
)