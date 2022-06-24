package com.hadi.myapplication.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.palette.graphics.Palette
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.ImageResult
import coil.request.SuccessResult

object PaletteGenerator {

    suspend fun convertImageUrlToBitmap(imageUrl: String, context: Context): ImageResult {

        val loader = ImageLoader(context)
        val request = ImageRequest.Builder(context)
            .data(imageUrl)
            .allowHardware(false)
            .build()

        return loader.execute(request)
    }

    fun extractColorsFromBitmap(bitmap: Bitmap): Map<String, String> {
        return mapOf(
            "vibrant" to parseColorSwatch(color = Palette.from(bitmap).generate().vibrantSwatch),
            "darkVibrant" to parseColorSwatch(color = Palette.from(bitmap)
                .generate().darkVibrantSwatch),
            "onDarkVibrant" to parseBodyColor(color = Palette.from(bitmap)
                .generate().darkVibrantSwatch?.bodyTextColor),

            )
    }

    private fun parseColorSwatch(color: Palette.Swatch?): String {
        return if (color != null) {
            val parsedColor = Integer.toHexString(color.rgb)
            return "#$parsedColor"
        } else {
            "#000000"
        }
    }

    private fun parseBodyColor(color: Int?): String {
        return if (color != null) {
            val parsedColor = Integer.toHexString(color)
            return "#$parsedColor"
        } else {
            "#FFFFFF"
        }
    }

}