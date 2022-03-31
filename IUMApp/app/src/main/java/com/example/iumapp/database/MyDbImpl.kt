package com.example.iumapp.database

import android.content.Context
import androidx.room.Room
import com.example.iumapp.database.lesson.Lesson
import com.example.iumapp.database.lesson.LessonDao
import com.example.iumapp.database.reservation.ReservationDao
import com.example.iumapp.database.teacher.TeacherDao
import com.example.iumapp.database.teaching.TeachingDao
import com.example.iumapp.database.user.User
import com.example.iumapp.database.user.UserDao

class MyDbImpl {
    private lateinit var myMyDb: MyDb

    private lateinit var userDao: UserDao
    private lateinit var teacherDao: TeacherDao
    private lateinit var lessonDao: LessonDao
    private lateinit var teachingDao: TeachingDao
    private lateinit var reservationDao: ReservationDao

    fun getDbInstance(context: Context): MyDb {
        myMyDb = Room.databaseBuilder(
            context,
            MyDb::class.java,
            "TestDB"
        ).allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
        setDao()

        deleteStudentDb()
        populateStudentInitData()
        populateLessonInitData()

        return myMyDb
    }

    private fun setDao() {
        userDao = myMyDb.studentDao()
        teacherDao = myMyDb.teacherDao()
        lessonDao = myMyDb.lessonDao()
        teachingDao = myMyDb.teachingDao()
        reservationDao = myMyDb.reservationDao()
    }

    private fun populateStudentInitData() {
        if(myMyDb.studentDao().getAll().isEmpty()) {
            userDao.insert(
                User(email = "oscar@gmail.com",
                    password = "password",
                    isAdmin = true),
                User(email = "dani@gmail.com",
                    password = "password",
                    isAdmin = false)
            )
        }
    }

    private fun populateLessonInitData(){
        if(myMyDb.lessonDao().getAll().isEmpty()) {
            lessonDao.insert(
                Lesson(name = "Architetture"),
                Lesson(name = "DB"),
                Lesson(name = "SAS"),
                Lesson(name = "SI"),
                Lesson(name = "Analisi"),
                Lesson(name = "Fisica"),
                Lesson(name = "CMRO"),
                Lesson(name = "ASD"),
                Lesson(name = "Architetture"),
                Lesson(name = "Architetture"),
                Lesson(name = "Architetture"),
                Lesson(name = "Architetture"),
                Lesson(name = "Architetture")
            )
        }
    }

    private fun deleteStudentDb() {
        userDao.nukeTable()
        teacherDao.nukeTable()
        lessonDao.nukeTable()
    }
}