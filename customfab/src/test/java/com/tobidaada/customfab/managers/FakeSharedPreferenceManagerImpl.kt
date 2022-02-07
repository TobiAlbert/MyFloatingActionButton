package com.tobidaada.customfab.managers

class FakeSharedPreferenceManagerImpl: ISharedPreferenceManager {

    private val localStorage = mutableMapOf<String, String>()

    override fun saveString(key: String, value: String) {
        localStorage[key] = value
    }

    override fun getString(key: String, default: String): String = localStorage[key] ?: default
}