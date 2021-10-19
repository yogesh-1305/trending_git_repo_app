package com.example.trendinggitrepos.data.model

import java.io.Serializable

data class CustomRepository(
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
