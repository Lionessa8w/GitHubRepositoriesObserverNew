package ru.marina.githubrepositoriesobservernew.detail

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryInfoRepository @Inject constructor(
    private val retrofit: RepositoryInfoProviderRetrofit
) {

    suspend fun getRepositoryInfo(
        token: String,
        owner: String,
        repo: String
    ): SingInResponseRepositoryInfo {
        return retrofit
            .getUserLoginProviderRetrofit()
            .getRepositoryInfo(
                token = "${Const.START_POINT} $token",
                owner = owner,
                repo = repo
            )
    }

    suspend fun getRepositoryContent(
        token: String,
        owner: String,
        repo: String
    ): SingInResponseRepositoryContent {
        return retrofit
            .getContent()
            .getContent(
                token = "${Const.START_POINT} $token",
                owner = owner,
                repo = repo
            )
    }
}