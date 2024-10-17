package com.example.prayforthem.utils

import android.content.Context
import android.view.View
import androidx.core.view.isVisible
import androidx.navigation.NavController
import com.example.prayforthem.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

object DialogConstructor {

    fun createExitDialog(
        context: Context,
        navController: NavController,
        view: View
    ): MaterialAlertDialogBuilder {

        val exitDialog = MaterialAlertDialogBuilder(context, R.style.CustomExitDialogTheme)
            .setTitle(R.string.close)
            .setMessage(R.string.are_you_sure_you_want_to_leave)
            .setPositiveButton(R.string.exit) { dialog, _ ->
                navController.popBackStack()
            }
            .setNegativeButton(R.string.cancel) { dialog, _ ->
                view.isVisible = false
            }
            .setCancelable(false)

        return exitDialog
    }
}