package ru.marina.githubrepositoriesobservernew.info

import javax.inject.Inject
import javax.inject.Singleton
import ru.marina.githubrepositoriesobservernew.detail.Const

@Singleton
class RepositoriesListRepository @Inject constructor(
    private val retrofit: RepositoriesListProviderRetrofit
) {

    suspend fun getRepositoriesList(token: String): List<SingInResponseRepositoryInfoEntity>{
        return retrofit.getUserLoginProviderRetrofit()
            .getRepositoriesInfoList("${Const.START_POINT} $token")

    }
}