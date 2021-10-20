package com.example.trendinggitrepos.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.trendinggitrepos.data.model.DatabaseRepository

@Database(entities = [DatabaseRepository::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

    abstract fun dbDao(): DatabaseDao
}