package ru.marina.githubrepositoriesobservernew

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class KeyValueStorageProvider {

    @Provides
    fun getKeyValueStorageSetting(): KeyValueStorageSetting {
        return KeyValueStorage()
    }

    @Provides
    fun getKeyValueStorageApi(): KeyValueStorageApi {
        return KeyValueStorage()
    }
}