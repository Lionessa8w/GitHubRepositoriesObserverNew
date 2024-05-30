package ru.marina.githubrepositoriesobservernew.info

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

interface RepositoriesInfoListApi {
    @GET("/user/repos")
    @Headers("Accept: application/vnd.github+json; X-GitHub-Api-Version: 2022-11-28")
    suspend fun getRepositoriesInfoList(@Header("Authorization") token: String): List<SingInResponseRepositoryInfoEntity>

}