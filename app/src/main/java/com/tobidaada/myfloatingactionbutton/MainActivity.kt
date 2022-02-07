package com.tobidaada.myfloatingactionbutton

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.tobidaada.customfab.ui.CustomFab

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rootView = findViewById<ConstraintLayout>(R.id.rootView)
        val customFab = CustomFab(this)

        rootView.addView(customFab)
    }
}