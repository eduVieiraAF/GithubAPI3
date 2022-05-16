package com.example.githubapi3.ui.model

import com.example.githubapi3.data.model.RepoData

class RepoVO(
    val name: String,
    val ownerLogin: String,
    val ownerAvatar: String,
    val description: String,
    val stargazerCount: String,
    val forksCount: String
) {
    companion object {
        fun fromResponse(repoData: RepoData) = RepoVO(
            repoData.name,
            repoData.owner.login,
            repoData.owner.avatarUrl,
            repoData.description ?: "\"→ no description found for this repository\"",
            repoData.stargazersCount,
            repoData.forksCount
        )
    }
}