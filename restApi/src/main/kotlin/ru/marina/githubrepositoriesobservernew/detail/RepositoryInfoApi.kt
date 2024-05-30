package ru.marina.githubrepositoriesobservernew.detail

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path

interface RepositoryInfoApi {
    @GET("/repos/{OWNER}/{REPO}")
    @Headers("Accept: application/vnd.github+json; X-GitHub-Api-Version: 2022-11-28")
    suspend fun getRepositoryInfo(
        @Header("Authorization") token: String,
        @Path("OWNER") owner: String,
        @Path("REPO") repo: String,
    ): SingInResponseRepositoryInfo

    @GET("/repos/{OWNER}/{REPO}/contents/README.md")
    @Headers("Accept: application/vnd.github+json; X-GitHub-Api-Version: 2022-11-28")
    suspend fun getContent(
        @Header("Authorization") token: String,
        @Path("OWNER") owner: String,
        @Path("REPO") repo: String,

        ): SingInResponseRepositoryContent
}