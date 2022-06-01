package com.codewithharry.countriesapp.roomhelpers

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CountryEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun countryDao(): CountryDao

    companion object {
        private var instance: AppDatabase? = null

        @Synchronized
        fun getInstance(mContext: Context): AppDatabase {
            if(instance == null){
                instance = Room.databaseBuilder(mContext.applicationContext,
                AppDatabase::class.java,"countries_database").build()
            }
            return instance!!
        }
    }
}