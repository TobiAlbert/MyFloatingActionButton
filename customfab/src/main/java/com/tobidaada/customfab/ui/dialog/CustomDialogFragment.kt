package com.tobidaada.customfab.ui.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialogFragment
import com.tobidaada.customfab.R

class CustomDialogFragment: AppCompatDialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val context = requireContext()
        val inflater = LayoutInflater.from(context)

        val view = inflater.inflate(R.layout.dialog_custom, null, false)

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