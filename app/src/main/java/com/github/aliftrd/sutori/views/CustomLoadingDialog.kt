package com.github.aliftrd.sutori.views

import android.app.Activity
import android.app.AlertDialog
import com.github.aliftrd.sutori.databinding.LayoutLoadingBinding

class CustomLoadingDialog(private val activity: Activity) {
    private lateinit var dialog: AlertDialog

    fun startLoadingDialog() {
        val builder = AlertDialog.Builder(activity)
        val inflater = activity.layoutInflater
        val binding = LayoutLoadingBinding.inflate(inflater)
        builder.setView(binding.root)
        builder.setCancelable(false)

        dialog = builder.create()
        dialog.show()
    }

    fun dismissDialog() {
        dialog.dismiss()
    }
}