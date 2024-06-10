package ru.marina.githubrepositoriesobservernew.detail

import javax.inject.Inject
import ru.marina.githubrepositoriesobservernew.ProviderRetrofit

class RepositoryInfoProviderRetrofit @Inject constructor() {

    @Inject
    lateinit var providerRetrofit: ProviderRetrofit

    fun getUserLoginProviderRetrofit(): RepositoryInfoApi {
        return providerRetrofit.providerRetrofit().create(RepositoryInfoApi::class.java)
    }

    fun getContent(): RepositoryInfoApi {
        return providerRetrofit.providerRetrofit().create(RepositoryInfoApi::class.java)
    }
}