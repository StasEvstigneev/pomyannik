package com.example.prayforthem.utils

import android.content.Context
import android.view.View
import androidx.core.view.isVisible
import com.example.prayforthem.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

object DialogConstructor {

    fun createExitDialog(
        context: Context,
        action: () -> Unit,
        message: String,
        view: View
    ): MaterialAlertDialogBuilder {
        val exitDialog = MaterialAlertDialogBuilder(context, R.style.CustomExitDialogTheme)
            .setTitle(R.string.close)
            .setMessage(message)
            .setPositiveButton(R.string.exit) { _, _ ->
                action()
            }
            .setNegativeButton(R.string.cancel) { _, _ ->
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
            .setPositiveButton(R.string.delete) { _, _ ->
                action()
                view.isVisible = false
            }
            .setNegativeButton(R.string.cancel) { _, _ ->
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
            .setMessage(message)
            .setPositiveButton(R.string.next) { _, _ ->
                action()
            }
            .setNegativeButton(R.string.cancel) { _, _ ->
                view.isVisible = false
            }
            .setCancelable(false)

        return navDialog
    }

}