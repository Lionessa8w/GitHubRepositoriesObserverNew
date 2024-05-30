package ru.marina.githubrepositoriesobservernew

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RetrofitProvider {

    @Provides
    fun getProviderRetrofit(): ProviderRetrofit {
        val interceptor = Interceptor()
        val providerClient = ProviderClient(interceptor)
        return ProviderRetrofitImpl(providerClient)
    }
}