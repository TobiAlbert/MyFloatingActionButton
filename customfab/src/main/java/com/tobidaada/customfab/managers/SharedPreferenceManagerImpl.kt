package com.tobidaada.customfab.managers

import android.content.Context
import android.content.SharedPreferences

class SharedPreferenceManagerImpl(private val context: Context): ISharedPreferenceManager {

    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
    }
    private val sharedPrefsEditor: SharedPreferences.Editor by lazy {
        sharedPreferences.edit()
    }

    companion object {
        private const val SHARED_PREF_NAME = "customfab__shared_pref_name"
    }

    override fun saveString(key: String, value: String) {
        sharedPrefsEditor.putString(key, value).apply()
    }

    override fun getString(key: String, default: String): String =
        sharedPreferences.getString(key, default) ?: default
}


interface ISharedPreferenceManager {
    fun saveString(key: String, value: String)
    fun getString(key: String, default: String = ""): String
}
