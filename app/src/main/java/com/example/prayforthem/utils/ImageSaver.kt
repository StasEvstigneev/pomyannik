package com.example.prayforthem.utils

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.core.content.FileProvider
import com.example.prayforthem.R
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

internal object ImageSaver {

    fun saveViewAsJpeg(context: Context, view: View, fileName: String): Boolean {
        val bitmap = getImageOfView(view)
        if (bitmap == null) {
            return false
        } else {
            try {
                saveImageToStorage(context, bitmap, fileName)
                return true
            } catch (e: Exception) {
                return false
            }
        }
    }

    private fun getImageOfView(view: View): Bitmap? {
        var image: Bitmap? = null
        try {
            image = Bitmap.createBitmap(
                view.measuredWidth,
                view.measuredHeight,
                Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(image)
            view.draw(canvas)
        } catch (e: Exception) {
            Log.e("Image fom view", "Failure")
        }
        return image
    }

    private fun saveImageToStorage(context: Context, bitmap: Bitmap, fileName: String) {
        var fos: OutputStream? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            context.contentResolver?.also { resolver ->
                val contentValues = ContentValues().apply {
                    put(MediaStore.Images.Media.DISPLAY_NAME, "$fileName.jpeg")
                    put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                    put(MediaStore.Images.Media.WIDTH, bitmap.width)
                    put(MediaStore.Images.Media.HEIGHT, bitmap.height)
                    put(
                        MediaStore.Images.Media.RELATIVE_PATH,
                        "${Constants.PICTURES_DIRECTORY}/${context.getString(R.string.app_name)}"
                    )
                }
                val imageUri: Uri? =
                    resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                fos = imageUri?.let {
                    resolver.openOutputStream(it)
                }
            }
        } else {
            val imagesDirectory = Environment
                .getExternalStoragePublicDirectory(
                    "${Constants.PICTURES_DIRECTORY}/${
                        context.getString(
                            R.string.app_name
                        )
                    }"
                )
            val image = File(imagesDirectory, fileName)
            fos = FileOutputStream(image)
        }
        fos?.use {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
        }
        fos?.close()

    }

    fun getUriFromView(context: Context, view: View): Uri? {
        val bitmap = getImageOfView(view)
        if (bitmap == null) {
            return null
        } else {
            return try {
                val file = File(context.cacheDir, "shared_image_${System.currentTimeMillis()}.jpg")
                FileOutputStream(file).use { outputStream ->
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                }
                FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
            } catch (e: Exception) {
                null
            }
        }
    }

}