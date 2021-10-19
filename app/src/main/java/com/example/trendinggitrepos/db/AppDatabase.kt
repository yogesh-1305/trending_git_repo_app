package com.example.trendinggitrepos.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.trendinggitrepos.data.model.entity.CustomRepository

@Database(entities = [CustomRepository::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

    abstract fun dbDao(): DatabaseDao
}