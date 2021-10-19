package com.example.trendinggitrepos.data.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "saved_repositories")
data class CustomRepository(
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
    val url: String? = ""
) : Serializable
