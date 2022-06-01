package com.codewithharry.countriesapp.roomhelpers

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.codewithharry.countriesapp.roomhelpers.CountryEntity

@Dao
interface CountryDao {
    @Query("SELECT * FROM countries")
    suspend fun getAll(): List<CountryEntity>

    @Insert
    suspend fun insertAll(countryEntity: MutableList<CountryEntity>)

    @Query("DELETE FROM countries")
    suspend fun deleteAll()
}