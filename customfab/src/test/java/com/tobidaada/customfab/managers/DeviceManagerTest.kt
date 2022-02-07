package com.tobidaada.customfab.managers

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class DeviceManagerTest {

    // dependencies
    private val sharedPrefsManager: FakeSharedPreferenceManagerImpl = FakeSharedPreferenceManagerImpl()

    // subject under test
    private lateinit var deviceManager: DeviceManager

    @Before
    fun setUp() {
        deviceManager = DeviceManager(sharedPrefsManager)
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun saveAppInstallationDate_isSavedInProperFormat() {
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
    fun getAppInstallationDate_returnsEmptyStringWhenNotSet() {
        // when the device manager is asked to retrieve the app installation date
        // without an initial set date
        val installationDate = deviceManager.getAppInstallationDate()

        // then the string returned should be empty
        assertThat(installationDate).isEmpty()
    }

    @Test
    fun getCurrentTimeFlow_emitsDateStringInProperFormat() = runTest {
        val dateString = deviceManager.getCurrentTime().first()

        println(dateString)

        val regexPattern = "\\d{2}:\\d{2}:\\d{2}\\s\\d{2}\\s[A-Z][a-z]+\\s\\d{4}"
        val re = Regex(regexPattern)

        assertThat(dateString.matches(re)).isTrue()
    }
}