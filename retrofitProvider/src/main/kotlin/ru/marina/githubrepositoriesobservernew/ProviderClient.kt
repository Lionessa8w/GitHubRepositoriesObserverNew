package ru.marina.githubrepositoriesobservernew

import javax.inject.Inject
import okhttp3.OkHttpClient

internal class ProviderClient @Inject constructor(private val interceptor: Interceptor) {

    fun provideClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor.createAuthorizationInterceptor())
            .addInterceptor(interceptor.createLoggingInterceptor())
            .build()
    }
}