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

    fun createDeleteDialog(
        context: Context,
        action: () -> Unit,
        view: View,
        message: String
    ): MaterialAlertDialogBuilder {
        val deleteDialog = MaterialAlertDialogBuilder(context, R.style.CustomExitDialogTheme)
            .setTitle(R.string.delete)
            .setMessage(message)
            .setPositiveButton(R.string.delete) { dialog, _ ->
                action()
                view.isVisible = false
            }
            .setNegativeButton(R.string.cancel) { dialog, _ ->
                view.isVisible = false
            }
            .setCancelable(false)

        return deleteDialog
    }

    fun createToPrayerNavigationDialog(
        context: Context,
        action: () -> Unit,
        message: String,
        view: View
    ): MaterialAlertDialogBuilder {
        val navDialog = MaterialAlertDialogBuilder(context, R.style.CustomExitDialogTheme)
//            .setTitle(R.string.close)
            .setMessage(message)
            .setPositiveButton(R.string.to_prayer) { dialog, _ ->
                action()
            }
            .setNegativeButton(R.string.cancel) { dialog, _ ->
                view.isVisible = false
            }
            .setCancelable(false)

        return navDialog
    }


}