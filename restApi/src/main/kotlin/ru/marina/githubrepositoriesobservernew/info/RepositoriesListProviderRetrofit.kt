package ru.marina.githubrepositoriesobservernew.info

import javax.inject.Inject
import ru.marina.githubrepositoriesobservernew.ProviderRetrofit

class RepositoriesListProviderRetrofit @Inject constructor() {

    @Inject
    lateinit var providerRetrofit: ProviderRetrofit

    fun getUserLoginProviderRetrofit(): RepositoriesInfoListApi {
        return providerRetrofit.providerRetrofit().create(RepositoriesInfoListApi::class.java)
    }
}