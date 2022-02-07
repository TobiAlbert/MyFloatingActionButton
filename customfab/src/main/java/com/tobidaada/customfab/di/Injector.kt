package com.tobidaada.customfab.di

import android.content.Context
import com.tobidaada.customfab.DeviceManager
import com.tobidaada.customfab.ISharedPreferenceManager
import com.tobidaada.customfab.SharedPreferenceManagerImpl

object Injector {

    lateinit var deviceManager: DeviceManager

    fun init(context: Context) {
        deviceManager = provideDeviceManager(context)
    }

    private fun provideSharedPreferenceManager(context: Context): ISharedPreferenceManager =
        SharedPreferenceManagerImpl(context)

    private fun provideDeviceManager(context: Context): DeviceManager = DeviceManager(provideSharedPreferenceManager(context))
}