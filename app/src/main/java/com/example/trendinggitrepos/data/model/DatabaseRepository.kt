package com.example.trendinggitrepos.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "saved_repositories", indices = [Index(value = ["url"], unique = true)])
data class DatabaseRepository(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val builtBy: String? = "",
    val username: String? = "",
    val repositoryName: String? = "",
    val description: String? = "",
    val forks: Int? = 0,
    val totalStars: Int? = 0,
    val language: String? = "",
    val languageColor: String? = "",
    @ColumnInfo(name = "url")
    val url: String? = ""
) : Serializable
