package com.example.trendinggitrepos.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.trendinggitrepos.data.model.entity.CustomRepository

@Dao
interface DatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRepo(repo: CustomRepository)

    @Query("select * from saved_repositories")
    fun getRepositories(): LiveData<List<CustomRepository>>

    @Delete
    suspend fun deleteRepo(repo: CustomRepository)
}