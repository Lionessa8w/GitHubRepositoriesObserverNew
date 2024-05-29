package ru.marina.githubrepositoriesobservernew

import javax.inject.Inject

internal class RepositoriesListProviderRetrofit {
    @Inject
    lateinit var providerRetrofit: ProviderRetrofit

    fun getUserLoginProviderRetrofit(): RepositoriesBriefInfoListApi {
        return providerRetrofit.providerRetrofit().create(RepositoriesBriefInfoListApi::class.java)
    }
}