package ru.marina.githubrepositoriesobservernew.auth

import javax.inject.Inject
import ru.marina.githubrepositoriesobservernew.ProviderRetrofit

class UserLoginProviderRetrofit @Inject constructor() {

    @Inject
    lateinit var providerRetrofit: ProviderRetrofit

    fun getUserLoginProviderRetrofit(): UserLoginApi {
        return providerRetrofit.providerRetrofit().create(UserLoginApi::class.java)
    }
}