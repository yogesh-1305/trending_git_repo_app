package com.example.trendinggitrepos.data.model

data class RepoApiResponseItem(
    val builtBy: List<BuiltBy>,
    val description: String? = "",
    val forks: Int? = 0,
    val language: String? = "",
    val languageColor: String? = "",
    val rank: Int? = 0,
    val repositoryName: String? = "",
    val since: String? = "",
    val starsSince: Int? = 0,
    val totalStars: Int? = 0,
    val url: String? = "",
    val username: String? = ""
) {
    companion object {
        fun RepoApiResponseItem.toCustomRepository(): DatabaseRepository {
            return DatabaseRepository(
                builtBy = this.builtBy[0].avatar ?: "",
                username = username,
                repositoryName = repositoryName,
                description = description,
                forks = forks,
                totalStars = totalStars,
                language = language,
                languageColor = languageColor,
                url = url
            )
        }
    }
}

