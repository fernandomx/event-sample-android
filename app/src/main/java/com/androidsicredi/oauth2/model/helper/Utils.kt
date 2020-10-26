package com.androidsicredi.oauth2.model.helper

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.ConnectivityManager
import java.io.ByteArrayOutputStream
import java.util.*

/**
@autor Fernando Meregali Xavier
@version 1.0
@date today

 */

object Utils {
    @JvmStatic
    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var available = false
        if (connectivityManager != null) {
            val networkInfo = connectivityManager.activeNetworkInfo
            available = networkInfo != null && networkInfo.isConnectedOrConnecting
        }
        return available
    }

    @JvmStatic
    fun getPictureByteOfArray(bitmap: Bitmap, typeBit: String): ByteArray {
        val byteArrayOutputStream = ByteArrayOutputStream()

        val dotposition: Int = typeBit.lastIndexOf(".")
        val format: String = typeBit.substring(dotposition + 1, typeBit.length)


        if (format == "png") {
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, byteArrayOutputStream)
        } else {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 0, byteArrayOutputStream)
        }
        return byteArrayOutputStream.toByteArray()
    }

    @JvmStatic
    fun getBitmapFromByte(image: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(image, 0, image.size)
    }

    @JvmStatic
    fun date(value: Long?): String {
        if(value == null) return ""
        val calendar = Calendar.getInstance(Locale.getDefault())
        calendar.timeInMillis = value
        return android.text.format.DateFormat.format("dd/MM/yyyy", calendar).toString()
    }

}