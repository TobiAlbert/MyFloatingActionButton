package com.tobidaada.customfab.managers

import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

@RunWith(JUnit4::class)
class DeviceManagerTest {

    // dependencies
    private val sharedPrefsManager: FakeSharedPreferenceManagerImpl = FakeSharedPreferenceManagerImpl()

    // subject under test
    private lateinit var deviceManager: DeviceManager

    @Before
    fun setUp() {
        deviceManager = DeviceManager(sharedPrefsManager)
    }

    @Test
    fun test_saveAppInstallationDate_isSavedInProperFormat() {
        // given a date object
        val localDate =
            LocalDateTime.of(2022, 2, 6, 12, 0, 0)
                .toInstant(ZoneOffset.UTC)

        val date = Date.from(localDate)

        // when the device manager is asked to save date
        deviceManager.saveAppInstallationDate(date)

        // then date is saved in expected format
        val expectedFormat = "06 Feb 2022"
        val actualFormat = deviceManager.getAppInstallationDate()

        assertThat(actualFormat).isEqualTo(expectedFormat)
    }

    @Test
    fun test_getAppInstallationDate_returnsEmptyStringWhenNotSet() {
        // when the device manager is asked to retrieve the app installation date
        // without an initial set date
        val installationDate = deviceManager.getAppInstallationDate()

        // then the string returned should be empty
        assertThat(installationDate).isEmpty()
    }
}