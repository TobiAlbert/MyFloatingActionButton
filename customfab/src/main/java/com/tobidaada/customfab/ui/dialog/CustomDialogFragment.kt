package com.tobidaada.customfab.ui.dialog

import android.content.res.Resources
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.tobidaada.customfab.R
import com.tobidaada.customfab.di.Injector
import com.tobidaada.customfab.managers.DeviceManager
import kotlinx.coroutines.launch

class CustomDialogFragment : AppCompatDialogFragment() {

    private val deviceManager: DeviceManager by lazy {
        Injector.deviceManager
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_custom, container, false)

        val installationTimeTv = view.findViewById<TextView>(R.id.customfab__installationDateTv)

        installationTimeTv.text = getString(
            R.string.library_installation_time_text,
            deviceManager.getAppInstallationDate()
        )

        displayCurrentTime(view)

        dialog?.window?.let {
            it.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            it.requestFeature(Window.FEATURE_NO_TITLE)
            Unit
        }

        return view
    }

    override fun onResume() {
        setWidthPercent(75)
        super.onResume()
    }

    private fun displayCurrentTime(view: View) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                val currentTimeTv = view.findViewById<TextView>(R.id.customfab__currentTimeTv)

                deviceManager.getCurrentTime().collect { dateString: String ->
                    currentTimeTv.text = getString(R.string.current_time_text, dateString)
                }
            }
        }
    }

    private fun AppCompatDialogFragment.setWidthPercent(percentage: Int) {
        val percent = percentage.toFloat() / 100
        val dm = Resources.getSystem().displayMetrics
        val rect = dm.run { Rect(0, 0, widthPixels, heightPixels) }
        val percentWidth = rect.width() * percent
        dialog?.window?.setLayout(percentWidth.toInt(), ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    companion object {
        const val TAG: String = "CustomDialogFragmentTag"
        fun newInstance(): CustomDialogFragment {
            val fragment = CustomDialogFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            return fragment
        }
    }
}