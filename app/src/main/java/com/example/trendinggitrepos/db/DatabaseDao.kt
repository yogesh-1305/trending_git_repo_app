package com.example.trendinggitrepos.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.trendinggitrepos.data.model.DatabaseRepository

@Dao
interface DatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRepo(repo: DatabaseRepository)

    @Query("select * from saved_repositories")
    fun getRepositories(): LiveData<List<DatabaseRepository>>

    @Delete
    suspend fun deleteRepo(repo: DatabaseRepository)
}