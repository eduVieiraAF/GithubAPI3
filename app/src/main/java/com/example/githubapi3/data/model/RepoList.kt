package com.example.githubapi3.data.model

import com.google.gson.annotations.SerializedName

class RepoList(val items: List<RepoData>)

class RepoData(
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String?,
    @SerializedName("owner") val owner: RepoOwner,
    @SerializedName("forks_count") val forksCount: String,
    @SerializedName("stargazers_count") val stargazersCount: String
)

class RepoOwner(
    @SerializedName("login") val login: String,
    @SerializedName("avatar_url") val avatarUrl: String
)