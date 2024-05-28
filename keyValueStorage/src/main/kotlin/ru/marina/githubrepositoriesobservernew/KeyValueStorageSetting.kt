package ru.marina.githubrepositoriesobservernew

import android.content.Context
import android.content.SharedPreferences

interface KeyValueStorageSetting {

    fun bindDB(context: Context): SharedPreferences?

    fun releaseDB()

}