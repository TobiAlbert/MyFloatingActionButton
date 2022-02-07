package com.tobidaada.customfab.managers

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.text.SimpleDateFormat
import java.util.*

class DeviceManager(private val sharedPreferenceManager: ISharedPreferenceManager) {

    companion object {
        private const val INSTALLATION_DATE_KEY = "installation_time_key"
    }

    fun getCurrentTime(): Flow<String> = flow {
        while (true) {
            val dateString = convertDateToString(Date(), DateFormat.HH_MM_SS_DD_MMM_YYYY)
            emit(dateString)

            kotlinx.coroutines.delay(1_000)
        }
    }.flowOn(Dispatchers.Main)

    fun getAppInstallationDate(): String {
        val dateString = sharedPreferenceManager.getString(INSTALLATION_DATE_KEY)

        val date = Date()
        if (dateString.isEmpty()) {
            saveAppInstallationDate(date)
            return convertDateToString(date, DateFormat.DD_MMM_YYYY)
        }

        return dateString
    }

    fun saveAppInstallationDate(date: Date) {
        val dateString = convertDateToString(date, DateFormat.DD_MMM_YYYY)
        sharedPreferenceManager.saveString(INSTALLATION_DATE_KEY, dateString)
    }

    private fun convertDateToString(date: Date, dateFormat: DateFormat): String {
        val formatter = SimpleDateFormat(dateFormat.pattern, Locale.US)
        return formatter.format(date)
    }

    enum class DateFormat(val pattern: String) {
        // 05 Mar 2022
        DD_MMM_YYYY("dd MMM yyyy"),

        // 12:12:12 06 Mar 2022
        HH_MM_SS_DD_MMM_YYYY("hh:mm:ss dd MMM yyyy")
    }

}