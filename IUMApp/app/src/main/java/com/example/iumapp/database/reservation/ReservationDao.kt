package com.example.iumapp.database.reservation

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.iumapp.database.MyDb
import com.example.iumapp.database.MyDbFactory

@Dao
interface ReservationDao {
    @Insert
    fun insert(vararg reservation: Reservation)

    @Delete
    fun delete(reservation: Reservation)

    @Query("SELECT * FROM reservation")
    fun getAll(): List<Reservation>

    @Query("DELETE FROM reservation")
    fun nukeTable();

    @Query("SELECT teacher FROM reservation WHERE lesson = :lessonName AND day = :dayName AND time_slot = :time_slotVal")
    fun getUnavailableTeacher(lessonName: String,
                              dayName: String,
                              time_slotVal: Int): List<String>

    @Query("SELECT * FROM reservation WHERE user = :userName")
    fun provideReservationByUser(userName: String): List<Reservation>

    //TODO solve logic problem here. lesson with no teachers are still showing
    fun provideAvailableLessons(myDb: MyDb,
                                day: String,
                                time_slot: Int): List<String>
    {
        val lessonList = myDb.lessonDao().getAll()
        val resList: MutableList<String> = mutableListOf()

        lessonList.forEach { if (provideAvailableTeachers(myDb, it, day, time_slot).isNotEmpty()) resList.add(it) }

        return resList
    }

    fun provideAvailableTeachers(myDb: MyDb,
                                 lesson: String,
                                 day: String,
                                 time_slot: Int): List<String>
    {
        val teacherList = myDb.teachingDao().getTeacherByLesson(lesson).toMutableList()
        for (teacher in MyDbFactory
            .getMyDbInstance()
            .reservationDao()
            .getUnavailableTeacher(lesson, day, time_slot)
        ){
            teacherList.remove(teacher)
        }

        return teacherList
    }
}