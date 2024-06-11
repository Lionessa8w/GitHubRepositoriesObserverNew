package ru.marina.githubrepositoriesobservernew

import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor


internal class Interceptor {

    fun createAuthorizationInterceptor(): Interceptor {
        return Interceptor { chain ->
            val newBuilder = chain.request().newBuilder()
                .also { it.header(Const.ACCEPT_HEADER, Const.ACCEPT) }
            return@Interceptor chain.proceed(newBuilder.build())
        }
    }

    fun createLoggingInterceptor(): Interceptor {
        return HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
    }
}