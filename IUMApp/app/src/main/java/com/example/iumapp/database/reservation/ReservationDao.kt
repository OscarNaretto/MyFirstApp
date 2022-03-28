package com.example.iumapp.database.reservation

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

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
}