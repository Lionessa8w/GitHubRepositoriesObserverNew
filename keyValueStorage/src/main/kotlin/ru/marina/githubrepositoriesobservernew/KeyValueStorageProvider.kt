package ru.marina.githubrepositoriesobservernew

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class KeyValueStorageProvider {

    private val keyValueStorage = KeyValueStorage()

    @Provides
    fun getKeyValueStorageSetting(): KeyValueStorageSetting {
        return keyValueStorage
    }

    @Provides
    fun getKeyValueStorageApi(): KeyValueStorageApi {
        return keyValueStorage
    }
}