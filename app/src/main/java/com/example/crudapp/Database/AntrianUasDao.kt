package com.example.crudapp.Database

import androidx.room.*

@Dao
interface AntrianUasDao {
    @Insert
    suspend fun addAntrian(antrianUas: AntrianUas)

    @Update
    suspend fun updateAntrian(antrianuas: AntrianUas)

    @Delete
    suspend fun deleteAntrian(antrianuas: AntrianUas)

    @Query("SELECT * FROM antrian")
    suspend fun getAllAntrian(): List<AntrianUas>

    @Query("SELECT * FROM antrian WHERE id=:antrian_id")
    suspend fun getAntrian(antrian_id: Int) : List<AntrianUas>

}