package com.tobidaada.customfab.managers

import java.text.SimpleDateFormat
import java.util.*

class DeviceManager(private val sharedPreferenceManager: ISharedPreferenceManager) {

    companion object {
        private const val INSTALLATION_DATE_KEY = "installation_time_key"
    }

    // get the current time (figure out how to convert this to a flow/observable)
    fun getCurrentTime(): String =
        convertDateToString(Date(), DateFormat.HH_MM_SS_DD_MMM_YYYY)

    // get the installation time
    fun getAppInstallationDate(): String {
        val dateString = sharedPreferenceManager.getString(INSTALLATION_DATE_KEY)

        val date = Date()
        if (dateString.isEmpty()) {
            saveAppInstallationDate(date)
            return convertDateToString(date, DateFormat.DD_MMM_YYYY)
        }

        return dateString
    }

    // save the installation time
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