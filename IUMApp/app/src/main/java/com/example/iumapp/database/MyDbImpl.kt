package com.example.dbtest.database

import android.content.Context
import androidx.room.Room
import com.example.dbtest.database.lesson.LessonDao
import com.example.dbtest.database.reservation.ReservationDao
import com.example.dbtest.database.teacher.TeacherDao
import com.example.dbtest.database.teaching.TeachingDao
import com.example.dbtest.database.user.User
import com.example.dbtest.database.user.UserDao
import javax.inject.Singleton


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
                User(email = "oscar@gmai.com",
                    password = "password",
                    isAdmin = true),
                User(email = "dani@gmai.com",
                    password = "password",
                    isAdmin = false)
            )
        }
    }

    private fun deleteStudentDb() {
        userDao.nukeTable()
        teacherDao.nukeTable()
        lessonDao.nukeTable()
    }
}