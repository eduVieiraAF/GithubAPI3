package com.example.githubapi3.data.api

import com.example.githubapi3.data.model.RepoList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetroServiceInterface {

    @GET("repositories")
    suspend fun getDataFromAPI(
        @Query("q") query: String, @Query("page") page: Int,
        @Query("per_page") pageSize: Int = 20
    ): Response<RepoList>
}