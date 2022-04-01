package com.example.iumapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.iumapp.database.lesson.Lesson
import com.example.iumapp.database.lesson.LessonDao
import com.example.iumapp.database.reservation.Reservation
import com.example.iumapp.database.reservation.ReservationDao
import com.example.iumapp.database.teacher.Teacher
import com.example.iumapp.database.teacher.TeacherDao
import com.example.iumapp.database.teaching.Teaching
import com.example.iumapp.database.teaching.TeachingDao
import com.example.iumapp.database.user.User
import com.example.iumapp.database.user.UserDao

@Database(entities = [User::class, Teacher::class, Lesson::class, Teaching::class, Reservation::class], version = 6)
abstract class MyDb: RoomDatabase() {
    abstract fun studentDao(): UserDao
    abstract fun teacherDao(): TeacherDao
    abstract fun lessonDao(): LessonDao
    abstract fun reservationDao(): ReservationDao
    abstract fun teachingDao(): TeachingDao
}