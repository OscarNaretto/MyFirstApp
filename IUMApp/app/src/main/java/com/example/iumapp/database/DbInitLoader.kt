package com.example.iumapp.database

import com.example.iumapp.database.lesson.Lesson
import com.example.iumapp.database.lesson.LessonDao
import com.example.iumapp.database.teacher.Teacher
import com.example.iumapp.database.teacher.TeacherDao
import com.example.iumapp.database.teaching.Teaching
import com.example.iumapp.database.teaching.TeachingDao
import com.example.iumapp.database.user.User
import com.example.iumapp.database.user.UserDao

class DbInitLoader(val myDb: MyDb) {
    private var userDao: UserDao = myDb.userDao()
    private var teacherDao: TeacherDao = myDb.teacherDao()
    private var lessonDao: LessonDao = myDb.lessonDao()
    private var teachingDao: TeachingDao = myDb.teachingDao()

    fun populateInitData(){
        populateStudentInitData()
        populateTeacherInitData()
        populateLessonInitData()
        populateTeachingInitData()
    }

    private fun populateStudentInitData() {
        if(myDb.userDao().getAll().isEmpty()) {
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
        if(myDb.teacherDao().getAll().isEmpty()) {
            teacherDao.insert(
                Teacher("Rossano Gaeta"),
                Teacher("Michele Garetto"),
                Teacher("Maurizio Lucenteforte"),
                Teacher("Daniele Paolo Radicioni"),
                Teacher("Claudio Schifanella"),

                Teacher("Roberto Aringhieri"),
                Teacher("Andrea Cesare Grosso"),

                Teacher("Andrea Mori"),

                Teacher("Luca Motto Ros"),

                Teacher("Ciro Cattuto"),
                Teacher("Liliana Ardissono"),

                )
        }
    }

    private fun populateLessonInitData(){
        if(myDb.lessonDao().getAll().isEmpty()) {
            lessonDao.insert(
                Lesson(
                    name = "Architetture degli Elaboratori",
                    description = "L???insegnamento ha lo scopo di introdurre i concetti e le tecniche fondamentali per l???analisi e la progettazione di algoritmi, che sono alla base dello sviluppo del software. Gli studenti acquisiranno conoscenze circa l???analisi di correttezza e complessit?? computazionale degli algoritmi, sulle strutture dati per la rappresentazione dell???informazione, sulle tecniche di problem-solving mediante lo sviluppo di algoritmi efficienti. L???insegnamento ?? supportato da un laboratorio che ne costituisce parte integrante, finalizzato alla realizzazione e sperimentazione degli algoritmi e delle strutture dati mediante un linguaggio imperativo ed uno object-oriented."
                ),

                Lesson(
                    name = "Calcolo matriciale e ricerca operativa",
                    description = "Il corso si propone di fornire agli studenti nozioni generali di calcolo matriciale, algebra e geometria, e nozioni pi?? specifiche di ricerca operativa.\n" +
                            "Il calcolo matriciale ?? uno strumento fondamentale per il calcolo scientifico. La ricerca operativa studia modelli e metodi, basati sulle tecniche introdotte, per l'utilizzo ottimale di risorse scarse (in ambiti produttivi, finanziari, ecc.)."
                ),

                Lesson(
                    name = "Matematica discreta",
                    description = "L???insegnamento si propone di fornire allo studente una introduzione alla matematica discreta, con particolare riguardo per gli aspetti pi?? rilevanti per la formazione di base di un informatico, in particolare una adeguata familiarit?? con le strutture algebriche, il calcolo combinatorio e le principali tecniche di dimostrazione."
                ),

                Lesson(
                    name = "Logica",
                    description = "L???insegnamento si propone di fornire allo studente una introduzione alla logica matematica, con particolare riguardo per i suoi aspetti pi?? rilevanti per la formazione di base di un informatico, in particolare una adeguata familiarit?? con le strutture algebriche e le principali tecniche di dimostrazione."
                ),

                Lesson(
                    name = "Programmazione 2",
                    description = "L???insegnamento ha l'obiettivo di approfondire concetti di informatica di base e in particolare di fornire una introduzione al paradigma di programmazione a oggetti. Si propone quindi di raffinare le capacit?? di programmare nel linguaggio Java apprese nel corso di Programmazione I e di introdurre le nozioni fondamentali della programmazione orientata agli oggetti. In particolare, il corso illustrer?? le astrazioni fondamentali per la progettazione del software (classi e oggetti), la definizione di semplici strutture dati (liste, alberi, pile, code) e operazioni corrispondenti, i meccanismi di base per favorire riuso e modularit?? del software (ereditariet??, polimorfismo, tipi generici), la specifica degli invarianti di classe e gestione delle loro violazioni (asserzioni ed eccezioni), cos?? come alcune classi fondamentali della libreria Java. Si dar?? particolare enfasi agli aspetti di buona progettazione del software, utilizzando concetti presi a prestito dall'ingegneria del software e formalismi grafici quali UML."
                ),
            )
        }
    }

    private fun populateTeachingInitData(){
        if(myDb.teachingDao().getAll().isEmpty()) {
            teachingDao.insert(

                Teaching("Architetture degli Elaboratori", "Idilio Drago"),
                Teaching("Architetture degli Elaboratori", "Rossano Gaeta"),
                Teaching("Architetture degli Elaboratori", "Michele Garetto"),
                Teaching("Architetture degli Elaboratori", "Maurizio Lucenteforte"),
                Teaching("Architetture degli Elaboratori", "Daniele Paolo Radicioni"),
                Teaching("Architetture degli Elaboratori", "Claudio Schifanella"),

                Teaching("Calcolo matriciale e ricerca operativa", "Roberto Aringhieri"),
                Teaching("Calcolo matriciale e ricerca operativa", "Andrea Cesare Grosso"),

                Teaching("Matematica discreta", "Andrea Mori"),

                Teaching("Logica", "Luca Motto Ros"),

                Teaching("Programmazione 2", "Ciro Cattuto"),
                Teaching("Programmazione 2", "Liliana Ardissono"),

                )
        }
    }

}