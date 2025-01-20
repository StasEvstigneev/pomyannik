package com.example.prayforthem.utils

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import androidx.core.content.FileProvider
import com.example.prayforthem.R
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

internal object ImageSaver {

    fun saveViewAsJpeg(context: Context, view: View, fileName: String): Boolean {
        try {
            val bitmap = getImageOfView(context, view)
            if (bitmap == null) {
                Log.e("Save JPEG", "bitmap = null")
                return false
            } else {
                try {
                    saveImageToStorage(context, bitmap, fileName)
                    return true
                } catch (e: Exception) {
                    Log.e("Save JPEG", "$e")
                    return false
                }
            }
        } catch (e: Exception) {
            Log.e("Save JPEG", "$e")
            return false
        }

    }

    private fun getImageOfView(context: Context, view: View): Bitmap? {
        val originalViewParams = view.layoutParams
        val originalWidth = originalViewParams.width
        val originalHeight = originalViewParams.height
        Log.d("View measure", "Original W = $originalWidth, original H = $originalHeight")

        val densityDpi = context.resources.displayMetrics.densityDpi
        Log.d("View measure", "Density Dpi = $densityDpi")

        var fixedWidthPx = context.resources.getDimensionPixelSize(R.dimen.screenshot_width)
        var fixedHeightPx = context.resources.getDimensionPixelSize(R.dimen.screenshot_height)

        if (!isDeviceTablet(context)) {
            val fixedPhoneSizes = getPhoneFixedSizes(densityDpi, context)
            fixedWidthPx = fixedPhoneSizes.first
            fixedHeightPx = fixedPhoneSizes.second
            Log.d(
                "View measure",
                "It is phone. Phone Width = $fixedWidthPx; Phone Height = $fixedHeightPx"
            )
        } else {
            fixedWidthPx = context.resources.getDimensionPixelSize(R.dimen.screenshot_width_tablet)
            fixedHeightPx =
                context.resources.getDimensionPixelSize(R.dimen.screenshot_height_tablet)
            Log.d(
                "View measure",
                "It is tablet. Tablet Width = $fixedWidthPx; Tablet Height = $fixedHeightPx"
            )
        }

        view.layoutParams = originalViewParams.apply {
            width = fixedWidthPx
            height = fixedHeightPx
        }
        view.measure(
            View.MeasureSpec.makeMeasureSpec(fixedWidthPx, View.MeasureSpec.EXACTLY),
            View.MeasureSpec.makeMeasureSpec(fixedHeightPx, View.MeasureSpec.EXACTLY)
        )
        view.layout(0, 0, fixedWidthPx, fixedHeightPx)

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
            Log.e("Get Image of View", "$e")
        }

        view.layoutParams = originalViewParams.apply {
            width = originalWidth
            height = originalHeight
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
            if (!imagesDirectory.exists()) {
                imagesDirectory.mkdirs()
            }
            Log.e("Save JPEG", "Does directory exist? ${imagesDirectory.exists()}")
            val image = File(imagesDirectory, "$fileName.jpeg")
            fos = FileOutputStream(image)
        }
        fos?.use {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
        }
        fos?.close()

    }

    fun getUriFromView(context: Context, view: View): Uri? {
        val bitmap = getImageOfView(context, view)
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

    private fun getPhoneFixedSizes(densityDpi: Int, context: Context): Pair<Int, Int> {
        val sizes = when (densityDpi) {
            in DisplayMetrics.DENSITY_XXHIGH..Int.MAX_VALUE -> {
                Pair(
                    first = context.resources.getDimensionPixelSize(R.dimen.screenshot_width),
                    second = context.resources.getDimensionPixelSize(R.dimen.screenshot_height)
                )
            }

            in DisplayMetrics.DENSITY_XHIGH until DisplayMetrics.DENSITY_XXHIGH -> {
                Pair(
                    first = context.resources.getDimensionPixelSize(R.dimen.screenshot_width_xhdpi),
                    second = context.resources.getDimensionPixelSize(R.dimen.screenshot_height_xhdpi)
                )
            }

            in DisplayMetrics.DENSITY_HIGH until DisplayMetrics.DENSITY_XHIGH -> {
                Pair(
                    first = context.resources.getDimensionPixelSize(R.dimen.screenshot_width_hdpi),
                    second = context.resources.getDimensionPixelSize(R.dimen.screenshot_height_hdpi)
                )
            }

            in DisplayMetrics.DENSITY_MEDIUM until DisplayMetrics.DENSITY_HIGH -> {
                Pair(
                    first = context.resources.getDimensionPixelSize(R.dimen.screenshot_width_mdpi),
                    second = context.resources.getDimensionPixelSize(R.dimen.screenshot_height_mdpi)
                )
            }

            in DisplayMetrics.DENSITY_LOW until DisplayMetrics.DENSITY_MEDIUM -> {
                Pair(
                    first = context.resources.getDimensionPixelSize(R.dimen.screenshot_width_ldpi),
                    second = context.resources.getDimensionPixelSize(R.dimen.screenshot_height_ldpi)
                )
            }

            else -> {
                Pair(
                    first = context.resources.getDimensionPixelSize(R.dimen.screenshot_width_ldpi),
                    second = context.resources.getDimensionPixelSize(R.dimen.screenshot_height_ldpi)
                )
            }
        }
        Log.d("View measure", "Sizes. Phone Width = ${sizes.first}; Phone Height = ${sizes.second}")
        return sizes
    }

    private fun isDeviceTablet(context: Context): Boolean {
        val resources = context.resources
        val displayMetrics = resources.displayMetrics
        val screenWidthPx = displayMetrics.widthPixels.toFloat()
        Log.d("View measure", "screenWidthPx = $screenWidthPx")

        val scale = resources.displayMetrics.density
        val minWidthDp = context.resources.getDimensionPixelSize(R.dimen.tab_smallest_width) / scale
        val screenWidthDp = (screenWidthPx / scale).toInt()

        Log.d("View measure", "screenWidthDp = $screenWidthDp")
        Log.d("View measure", "minWidthDp = $minWidthDp")
        Log.d("View measure", "isTablet? = ${(screenWidthDp >= minWidthDp)}")

        return screenWidthDp >= minWidthDp
    }

}