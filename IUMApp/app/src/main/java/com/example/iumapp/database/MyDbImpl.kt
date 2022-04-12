package com.example.iumapp.database

import android.content.Context
import androidx.room.Room
import com.example.iumapp.database.lesson.Lesson
import com.example.iumapp.database.lesson.LessonDao
import com.example.iumapp.database.reservation.ReservationDao
import com.example.iumapp.database.teacher.Teacher
import com.example.iumapp.database.teacher.TeacherDao
import com.example.iumapp.database.teaching.Teaching
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

        dbCleanup()
        DbInitLoader(myMyDb).populateInitData()

        return myMyDb
    }

    private fun setDao() {
        userDao = myMyDb.userDao()
        teacherDao = myMyDb.teacherDao()
        lessonDao = myMyDb.lessonDao()
        teachingDao = myMyDb.teachingDao()
        reservationDao = myMyDb.reservationDao()
    }

    private fun dbCleanup() {
        userDao.nukeTable()
        teacherDao.nukeTable()
        lessonDao.nukeTable()
        teachingDao.nukeTable()
        reservationDao.nukeTable()
    }
}