package com.example.prayforthem.listingdisplay.data

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.example.prayforthem.R
import com.example.prayforthem.listingdisplay.domain.ExternalNavigator

class ExternalNavigatorImpl(private val context: Context) : ExternalNavigator {
    override fun shareListAsText(title: String, names: List<String>) {
        val listing = prepareListText(title, names)
        val shareListingIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, listing)
        }
        context.startActivity(
            Intent
                .createChooser(shareListingIntent, context.getString(R.string.share_list_as_text))
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        )
    }

    override fun shareListAsJpeg(image: Uri) {
        val shareImageIntent = Intent(Intent.ACTION_SEND).apply {
            type = "image/jpg"
            putExtra(Intent.EXTRA_STREAM, image)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        context.startActivity(
            Intent.createChooser(shareImageIntent, null)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        )
    }

    private fun prepareListText(title: String, list: List<String>): String {
        var result = "${title.uppercase()}:"
        list.forEach { name ->
            result += "\n$name"
        }
        return result
    }

}