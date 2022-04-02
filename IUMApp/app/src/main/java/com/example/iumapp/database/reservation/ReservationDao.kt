package com.example.iumapp.database.reservation

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.iumapp.database.MyDb

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

    @Query("SELECT teacher from reservation WHERE lesson = :lessonName AND day = :dayName AND time_slot = :time_slotVal AND status = :statusVal")
    fun getAvailableTeacher(lessonName: String,
                            dayName: String,
                            time_slotVal: Int,
                            statusVal: String): List<String>

    fun provideAvailableLessons(myDb: MyDb,
                                day: String,
                                time_slot: Int,
                                status: String): List<String>{
        val lessonList = myDb.lessonDao().getAll()
        val resList: MutableList<String> = mutableListOf()

        lessonList.forEach { if (provideAvailableTeachers(myDb, it, day, time_slot, status).isNotEmpty()) resList.add(it) }

        return resList
    }

    fun provideAvailableTeachers(myDb: MyDb,
                                 lesson: String,
                                 day: String,
                                 time_slot: Int,
                                 status: String): List<String>
    {
        val teacherList = myDb.teachingDao().getTeacherByLesson(lesson)
        val resList: MutableList<String> = mutableListOf()

        teacherList.forEach {
            if (myDb
                    .reservationDao()
                    .getAvailableTeacher(
                        lesson,
                        day,
                        time_slot,
                        status
                    )
                    .isEmpty()
            ) resList.add(it)
        }

        return resList
    }
}