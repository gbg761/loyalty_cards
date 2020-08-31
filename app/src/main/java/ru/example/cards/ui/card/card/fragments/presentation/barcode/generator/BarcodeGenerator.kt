package ru.example.cards.ui.card.card.fragments.presentation.barcode.generator

import android.graphics.Bitmap
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import java.util.*

class BarcodeGenerator {

    private val WHITE = -0x1
    private val BLACK = -0x1000000

    @Throws(WriterException::class)
    fun encodeAsBitmap(
        contents: String,
        format: BarcodeFormat,
        imgWidth: Int,
        imgHeight: Int
    ): Bitmap? {

        var hints: MutableMap<EncodeHintType, Any>? = null
        val encoding = guessAppropriateEncoding(contents)

        if (encoding != null) {
            hints = EnumMap<EncodeHintType, Any>(EncodeHintType::class.java)
            hints[EncodeHintType.CHARACTER_SET] = encoding
        }

        val writer = MultiFormatWriter()
        val result: BitMatrix

        try {
            result = writer.encode(contents, format, imgWidth, imgHeight, hints)
        } catch (e: IllegalArgumentException) {
            // Unsupported format
            return null
        }

        val width = result.width
        val height = result.height

        val pixels = IntArray(width * height)
        for (y in 0 until height) {
            val offset = y * width
            for (x in 0 until width) {
                pixels[offset + x] = if (result.get(x, y)) BLACK else WHITE
            }
        }

        val bitmap = Bitmap.createBitmap(
            width, height,
            Bitmap.Config.ARGB_8888
        )
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height)
        return bitmap
    }

    private fun guessAppropriateEncoding(contents: CharSequence): String? {
        // Very crude at the moment
        for (element in contents) {
            if (element.toInt() > 0xFF) {
                return "UTF-8"
            }
        }
        return null
    }
}