package ru.marina.githubrepositoriesobservernew

interface KeyValueStorageApi {

    fun getToken(): String

    fun setToken(token: String)
}

