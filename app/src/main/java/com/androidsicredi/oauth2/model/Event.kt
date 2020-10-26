package com.androidsicredi.oauth2.model

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.google.gson.annotations.Expose
import java.io.*
import kotlin.jvm.Throws

class Event: Serializable {
    lateinit var imageByteArray: ByteArray

    @Expose
    var date: Long = 0

    @Expose
    var description: String? = null

    @Expose
    var price = 0.0

    @Expose
    var title: String? = null

    @Expose
    var id: String? = null


    @Expose
    var image: String? = null

    var picture: Bitmap? = null

    @Throws(IOException::class)
    private fun writeObject(out: ObjectOutputStream) {
        out.writeObject(date)
        out.writeObject(description)
        out.writeObject(price)
        out.writeObject(title)
        out.writeObject(id)
        out.writeObject(image)
        val byteStream = ByteArrayOutputStream()
        picture!!.compress(Bitmap.CompressFormat.PNG, 0, byteStream)
        val bitmapBytes = byteStream.toByteArray()
        out.write(bitmapBytes, 0, bitmapBytes.size)
    }

    @Throws(IOException::class, ClassNotFoundException::class)
    private fun readObject(`in`: ObjectInputStream) {
        date = `in`.readObject() as Long
        description = `in`.readObject() as String
        price = `in`.readObject() as Double
        title = `in`.readObject() as String
        id = `in`.readObject() as String
        image = `in`.readObject() as String
        val byteStream = ByteArrayOutputStream()
        var b: Int
        while (`in`.read().also { b = it } != -1) byteStream.write(b)
        val bitmapBytes = byteStream.toByteArray()
        picture = BitmapFactory.decodeByteArray(bitmapBytes, 0,
                bitmapBytes.size)
    }





}