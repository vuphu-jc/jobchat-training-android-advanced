package com.example.advancedandroid
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Environment
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.util.concurrent.Semaphore

interface ImageUriRepository {
    fun getImageUri() : MutableList<String>
}

class DeviceImageUriRepository(private val context: Context) : ImageUriRepository{
    private var cursor: Cursor

    init {
        val uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(android.provider.MediaStore.MediaColumns.DATA,
            android.provider.MediaStore.Images.Media.BUCKET_DISPLAY_NAME)
        cursor = context.contentResolver.query(uri, projection, null, null, null) as Cursor
    }

    override fun getImageUri(): MutableList<String> {
        val columnIndexData = cursor.getColumnIndex(android.provider.MediaStore.MediaColumns.DATA)
        cursor.moveToFirst()
        val result = mutableListOf<String>()
        while (cursor.moveToNext()) {
            val path= cursor.getString(columnIndexData)
            result.add(path)
        }
        result.reverse()
        return result
    }
}