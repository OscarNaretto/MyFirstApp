package com.example.dbtest.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.dbtest.database.lesson.Lesson
import com.example.dbtest.database.lesson.LessonDao
import com.example.dbtest.database.reservation.Reservation
import com.example.dbtest.database.reservation.ReservationDao
import com.example.dbtest.database.teacher.Teacher
import com.example.dbtest.database.teacher.TeacherDao
import com.example.dbtest.database.teaching.Teaching
import com.example.dbtest.database.teaching.TeachingDao
import com.example.dbtest.database.user.User
import com.example.dbtest.database.user.UserDao

@Database(entities = [User::class, Teacher::class, Lesson::class, Teaching::class, Reservation::class], version = 5)
abstract class MyDb: RoomDatabase() {
    abstract fun studentDao(): UserDao
    abstract fun teacherDao(): TeacherDao
    abstract fun lessonDao(): LessonDao
    abstract fun reservationDao(): ReservationDao
    abstract fun teachingDao(): TeachingDao
}