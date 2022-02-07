package com.tobidaada.customfab.ui.dialog

import android.content.Context
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.PositionAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.tobidaada.customfab.R
import com.tobidaada.customfab.di.Injector
import com.tobidaada.customfab.managers.DeviceManager
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

@RunWith(AndroidJUnit4::class)
class CustomDialogFragmentTest {

    private lateinit var deviceManager: DeviceManager
    private lateinit var context: Context

    @Before
    fun setup() {
        context = InstrumentationRegistry.getInstrumentation().context
        Injector.init(context)

        deviceManager = Injector.deviceManager

        val scenario = launchFragmentInContainer<CustomDialogFragment>()

        scenario.moveToState(Lifecycle.State.RESUMED)
    }

    @Test
    fun testViewsInDialogAreVisible() {
        // assert all views are displayed
        onView(withId(R.id.imageView))
            .check(matches(ViewMatchers.isDisplayed()))

        onView(withId(R.id.customfab__currentTimeTv))
            .check(matches(ViewMatchers.isDisplayed()))

        onView(withId(R.id.customfab__installationDateTv))
            .check(matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun testPositionOfViewsInDialog() {
        // assert all views are displayed in proper position
        onView(withId(R.id.imageView))
            .check(PositionAssertions.isCompletelyAbove(withId(R.id.customfab__installationDateTv)))
            .check(PositionAssertions.isCompletelyAbove(withId(R.id.customfab__currentTimeTv)))

        onView(withId(R.id.customfab__installationDateTv))
            .check(PositionAssertions.isCompletelyBelow(withId(R.id.imageView)))
            .check(PositionAssertions.isCompletelyAbove(withId(R.id.customfab__currentTimeTv)))

        onView(withId(R.id.customfab__currentTimeTv))
            .check(PositionAssertions.isCompletelyBelow(withId(R.id.imageView)))
            .check(PositionAssertions.isCompletelyBelow(withId(R.id.customfab__installationDateTv)))
    }

    @Test
    fun testInstallationTimeIsShown() {
        // given a set date
        val date = Date()

        // when application installation date is set
        deviceManager.saveAppInstallationDate(date)

        // then ensure dialog displays proper text
        val dateString = deviceManager.getAppInstallationDate()
        val expectedText = context.getString(R.string.library_installation_time_text, dateString)

        onView(withId(R.id.customfab__installationDateTv))
            .check(matches(withText(expectedText)))
    }
}