package com.example.githubapi3.ui.model

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.githubapi3.data.model.RepoData
import com.example.githubapi3.data.model.RepoOwner
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class RepoVOTest {

    @Test
    fun repoData_mapper() {
        val repoData = RepoData(
            name = "Kotlin for beginners",
            description = "Simple repo",
            owner = RepoOwner(
                login = "kotlin101",
                avatarUrl = "url"
            ),
            forksCount = "1234",
            stargazersCount = "5678"
        )

        val repoVO = RepoVO.fromResponse(repoData)

        assertTrue(repoVO.name == "Kotlin for beginners")
        assertTrue(repoVO.description == "Simple repo")
        assertTrue(repoVO.ownerLogin == "kotlin101")
        assertTrue(repoVO.ownerAvatar == "url")
        assertTrue(repoVO.forksCount == "1234")
        assertTrue(repoVO.stargazerCount == "5678")
    }

    @Test
    fun repoData_mapper_with_null_description() {
        val repoData = RepoData(
            name = "Kotlin for beginners",
            description = null,
            owner = RepoOwner(
                login = "kotlin101",
                avatarUrl = "url"
            ),
            forksCount = "1234",
            stargazersCount = "5678"
        )

        val repoVO = RepoVO.fromResponse(repoData)

        assertTrue(repoVO.name == "Kotlin for beginners")
        assertTrue(repoVO.description == "\"→ no description found for this repository\"")
        assertTrue(repoVO.ownerLogin == "kotlin101")
        assertTrue(repoVO.ownerAvatar == "url")
        assertTrue(repoVO.forksCount == "1234")
        assertTrue(repoVO.stargazerCount == "5678")
    }
}