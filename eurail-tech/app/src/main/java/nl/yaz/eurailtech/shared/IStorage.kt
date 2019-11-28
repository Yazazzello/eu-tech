package nl.yaz.eurailtech.shared

import android.content.Context
import android.content.SharedPreferences
import java.lang.IllegalArgumentException
import kotlin.reflect.KClass

interface IStorage {
    suspend fun <T : Any> getValue(key: String, defaultValue: T, klass: KClass<T>): T
    suspend fun <T : Any> putValue(key: String, value: T, klass: KClass<T>)
}

class PreferencesStorageImpl(context: Context) : IStorage {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("EURAIL_PREFS", Context.MODE_PRIVATE)

    @Suppress("UNCHECKED_CAST")
    override suspend fun <T : Any> getValue(key: String, defaultValue: T, klass: KClass<T>): T {
        when (klass) {
            String::class -> return sharedPreferences.getString(key, defaultValue as String?) as T
            else -> throw IllegalArgumentException("wrong input")
        }
    }

    override suspend fun <T : Any> putValue(key: String, value: T, klass: KClass<T>) {
        when (klass) {
            String::class -> sharedPreferences.edit().putString(key, value as String?).apply()
            else -> throw IllegalArgumentException("wrong input")
        }
    }
}



