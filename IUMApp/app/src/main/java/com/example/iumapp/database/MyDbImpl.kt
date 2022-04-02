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
                Teacher("Rossano Gaeta"),
                Teacher("Michele Garetto"),
                Teacher("Maurizio Lucenteforte"),
                Teacher("Daniele Paolo Radicioni"),
                Teacher("Claudio Schifanella"),


                Teacher("Vivina Laura Barutello"),
                Teacher("Alberto Boscaggin"),
                Teacher("Gianluca Garello"),
                Teacher("Joerg Seiler"),
                Teacher("Stefano Vita"),

                Teacher("Roberto Aringhieri"),
                Teacher("Andrea Cesare Grosso"),


                Teacher("Andrea Mori"),

                Teacher("Luca Motto Ros"),

                Teacher("Luca Roversi"),

                Teacher("Ciro Cattuto"),
                Teacher("Liliana Ardissono"),


                Teacher("Ugo de' Liguoro"),
                Teacher("Idilio Drago"),
                Teacher("Andras Horvath"),
                Teacher("Diego Magro"),
                Teacher("Roberto Micalizio"),
                Teacher("GianLuca Pozzato"),

                Teacher("Ruggero Pensa"),
                Teacher("Luca Anselma"),
                Teacher("Noemi Mauro"),
                Teacher("Fabiana Vernero"),

                Teacher("Marco Pironti"),

                Teacher("Camillo Sacchetto"),
                Teacher("Fabio Montalcini"),

                Teacher("Igor Pesando"),

                Teacher("Luca Padovani"),
                Teacher("Jeremy James Sproston"),

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
                Teaching("Architetture degli Elaboratori", "Idilio Drago"),
                Teaching("Architetture degli Elaboratori", "Rossano Gaeta"),
                Teaching("Architetture degli Elaboratori", "Michele Garetto"),
                Teaching("Architetture degli Elaboratori", "Maurizio Lucenteforte"),
                Teaching("Architetture degli Elaboratori", "Daniele Paolo Radicioni"),
                Teaching("Architetture degli Elaboratori", "Claudio Schifanella"),

                Teaching("Analisi Matematica", "Vivina Laura Barutello"),
                Teaching("Analisi Matematica", "Alberto Boscaggin"),
                Teaching("Analisi Matematica", "Gianluca Garello"),
                Teaching("Analisi Matematica", "Joerg Seiler"),
                Teaching("Analisi Matematica", "Stefano Vita"),

                Teaching("Calcolo matriciale e ricerca operativa", "Roberto Aringhieri"),
                Teaching("Calcolo matriciale e ricerca operativa", "Andrea Cesare Grosso"),

                Teaching("Matematica discreta", "Andrea Mori"),

                Teaching("Logica", "Luca Motto Ros"),

                Teaching("Programmazione 1", "Luca Roversi"),

                Teaching("Programmazione 2", "Ciro Cattuto"),
                Teaching("Programmazione 2", "Liliana Ardissono"),


                Teaching("Algoritmi e strutture dati", "Ugo de' Liguoro"),
                Teaching("Algoritmi e strutture dati", "Idilio Drago"),
                Teaching("Algoritmi e strutture dati", "Andras Horvath"),
                Teaching("Algoritmi e strutture dati", "Diego Magro"),
                Teaching("Algoritmi e strutture dati", "Roberto Micalizio"),
                Teaching("Algoritmi e strutture dati", "GianLuca Pozzato"),

                Teaching("Basi di dati", "Ruggero Pensa"),
                Teaching("Basi di dati", "Luca Anselma"),
                Teaching("Basi di dati", "Noemi Mauro"),
                Teaching("Basi di dati", "Fabiana Vernero"),

                Teaching("Economia", "Marco Pironti"),

                Teaching("Diritto", "Camillo Sacchetto"),
                Teaching("Diritto", "Fabio Montalcini"),

                Teaching("Fisica", "Igor Pesando"),

                Teaching("Linguaggi formali e traduttori", "Luca Padovani"),
                Teaching("Linguaggi formali e traduttori", "Jeremy James Sproston"),

                Teaching("Elementi di probabilità e statistica", "Roberta Sirovich")
                )
        }
    }

    private fun deleteStudentDb() {
        userDao.nukeTable()
        teacherDao.nukeTable()
        lessonDao.nukeTable()
        teachingDao.nukeTable()
        reservationDao.nukeTable()
    }
}