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


    fun extractColorsFromBitmapToParsedColors(bitmap: Bitmap): ParsedColor {
        return ParsedColor(
            vibrant = parseColorSwatch(color = Palette.from(bitmap).generate().vibrantSwatch),
            lightVibrant = parseColorSwatch(color = Palette.from(bitmap)
                .generate().lightVibrantSwatch),
            darkVibrant = parseColorSwatch(color = Palette.from(bitmap)
                .generate().darkVibrantSwatch),
            muted = parseColorSwatch(color = Palette.from(bitmap)
                .generate().mutedSwatch),
            onDarkVibrant = parseBodyColor(color = Palette.from(bitmap)
                .generate().darkVibrantSwatch?.bodyTextColor)
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