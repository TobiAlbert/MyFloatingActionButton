package com.tobidaada.customfab.ui.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialogFragment
import com.tobidaada.customfab.managers.DeviceManager
import com.tobidaada.customfab.R
import com.tobidaada.customfab.di.Injector

class CustomDialogFragment: AppCompatDialogFragment() {

    private val deviceManager: DeviceManager by lazy {
        Injector.deviceManager
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val context = requireContext()
        val inflater = LayoutInflater.from(context)

        val view = inflater.inflate(R.layout.dialog_custom, null, false)

        val currentTimeTv = view.findViewById<TextView>(R.id.customfab__currentTimeTv)
        val installationTimeTv = view.findViewById<TextView>(R.id.customfab__installationDateTv)

        currentTimeTv.text = getString(R.string.current_time_text, deviceManager.getCurrentTime())
        installationTimeTv.text = getString(R.string.library_installation_time_text, deviceManager.getAppInstallationDate())

        return AlertDialog.Builder(context)
            .setView(view)
            .create()
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