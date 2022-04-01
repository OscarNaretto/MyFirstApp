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

        deleteStudentDb()
        populateStudentInitData()
        populateLessonInitData()
        populateTeacherInitData()
        populateTeachingInitData()

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
                User(email = "daniele@gmail.com",
                    password = "password",
                    isAdmin = false),
                User(email = "luca@gmail.com",
                    password = "password",
                    isAdmin = false)
            )
        }
    }

    private fun populateTeacherInitData(){
        if(myMyDb.teacherDao().getAll().isEmpty()) {
            teacherDao.insert(
                Teacher("Marco Aldinucci"),
                Teacher("Vivina Laura Barutello"),
                Teacher("Roberto Aringhieri"),
                Teacher("Andrea Mori"),
                Teacher("Luca Motto Ros"),
                Teacher("Luca Roversi"),
                Teacher("Ciro Cattuto"),
                Teacher("Ugo de' Liguoro"),
                Teacher("Ruggero Pensa"),
                Teacher("Marco Pironti"),
                Teacher("Camillo Sacchetto"),
                Teacher("Igor Pesando"),
                Teacher("Luca Padovani"),
                Teacher("Roberta Sirovich")
            )
        }
    }

    private fun populateLessonInitData(){
        if(myMyDb.lessonDao().getAll().isEmpty()) {
            lessonDao.insert(
                Lesson(name = "Architetture degli Elaboratori"),
                Lesson(name = "Analisi Matematica"),
                Lesson(name = "Calcolo matriciale e ricerca operativa"),
                Lesson(name = "Matematica discreta"),
                Lesson(name = "Logica"),
                Lesson(name = "Programmazione 1"),
                Lesson(name = "Programmazione 2"),
                Lesson(name = "Algoritmi e strutture dati"),
                Lesson(name = "Basi di dati"),
                Lesson(name = "Economia"),
                Lesson(name = "Diritto"),
                Lesson(name = "Fisica"),
                Lesson(name = "Linguaggi formali e traduttori"),
                Lesson(name = "Elementi di probabilità e statistica")
            )
        }
    }

    private fun populateTeachingInitData(){
        if(myMyDb.teachingDao().getAll().isEmpty()) {
            teachingDao.insert(
                Teaching("Architetture degli Elaboratori", "Marco Aldinucci"),
                Teaching("Analisi Matematica", "Vivina Laura Barutello"),
                Teaching("Calcolo matriciale e ricerca operativa", "Roberto Aringhieri"),
                Teaching("Matematica discreta", "Andrea Mori"),
                Teaching("Logica", "Luca Motto Ros"),
                Teaching("Programmazione 1", "Luca Roversi"),
                Teaching("Programmazione 2", "Ciro Cattuto"),
                Teaching("Algoritmi e strutture dati", "Ugo de' Liguoro"),
                Teaching("Basi di dati", "Ruggero Pensa"),
                Teaching("Economia", "Marco Pironti"),
                Teaching("Diritto", "Camillo Sacchetto"),
                Teaching("Fisica", "Igor Pesando"),
                Teaching("Linguaggi formali e traduttori", "Luca Padovani"),
                Teaching("Elementi di probabilità e statistica", "Roberta Sirovich")
                )
        }
    }



    private fun deleteStudentDb() {
        userDao.nukeTable()
        teacherDao.nukeTable()
        lessonDao.nukeTable()
    }
}