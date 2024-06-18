package ru.marina.githubrepositoriesobservernew

import android.content.Context
import android.content.SharedPreferences
import java.lang.IllegalStateException
import javax.inject.Singleton

private const val APP_PREFERENCES = "mySetting"
private const val TOKEN_KEY = "myToken"
private const val ERROR_DB = "errorDB"

@Singleton
internal class KeyValueStorage: KeyValueStorageApi, KeyValueStorageSetting {

    private var db: SharedPreferences? = null

    override fun bindDB(context: Context): SharedPreferences? {
        db = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
        return db
    }

    override fun releaseDB() {
        db = null
    }

    override fun getToken(): String {
        val currentBd = db ?: throw IllegalStateException(ERROR_DB)
        return currentBd.getString(TOKEN_KEY, "") ?: ""
    }

    override fun setToken(token: String) {
        val currentBd = db ?: throw IllegalStateException(ERROR_DB)
        currentBd.edit().putString(TOKEN_KEY, token).apply()
    }

    override fun clear() {
        val currentBd = db ?: throw IllegalStateException(ERROR_DB)
        currentBd.edit().putString(TOKEN_KEY, null).apply()
    }
}