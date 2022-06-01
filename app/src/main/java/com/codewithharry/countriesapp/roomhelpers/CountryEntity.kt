package com.codewithharry.countriesapp.roomhelpers

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "countries")
data class CountryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String?,
    val capital: String?,
    val flags: String?,
    val region: String?,
    val subregion: String?,
    val population: Int?,
)
