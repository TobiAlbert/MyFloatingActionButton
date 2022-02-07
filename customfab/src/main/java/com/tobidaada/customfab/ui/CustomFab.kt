package com.tobidaada.customfab.ui

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.View.OnClickListener
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.tobidaada.customfab.R
import com.tobidaada.customfab.di.Injector
import com.tobidaada.customfab.ui.dialog.CustomDialogFragment
import java.util.*

class CustomFab @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = com.google.android.material.R.attr.floatingActionButtonStyle,
): FloatingActionButton(context, attrs, defStyle) {

    init {

        // initialize project dependencies
        Injector.init(context)

        val deviceManager = Injector.deviceManager

        if (deviceManager.getAppInstallationDate().isEmpty()) {
            deviceManager.saveAppInstallationDate(Date())
        }

        // set icon drawable resource
        setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_calendar))

        val tint = ContextCompat.getColor(context, R.color.fab_image_tint)
        drawable.setTint(tint)

        // code to work on positioning of the fab

        setOnClickListener {}
    }

    override fun setOnClickListener(l: OnClickListener?) {
        // provide custom implementation for `OnClickListener` preventing outside
        // objects from setting it

        val clickListener = OnClickListener { view: View ->
            view.context?.let { viewContext: Context ->

                val fragmentManager = when (viewContext) {
                    is AppCompatActivity -> viewContext.supportFragmentManager
                    is Fragment -> viewContext.childFragmentManager
                    else -> null
                }

                if (fragmentManager != null) {
                    CustomDialogFragment.newInstance().show(fragmentManager, CustomDialogFragment.TAG)
                }
            }
        }

        super.setOnClickListener(clickListener)
    }
}