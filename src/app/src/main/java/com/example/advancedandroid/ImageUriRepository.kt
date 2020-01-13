package com.example.advancedandroid
import android.content.Context
import android.database.Cursor

interface ImageUriRepository {
    fun getImageUri() : MutableList<String>
}

class DeviceImageUriRepository(private val context: Context) : ImageUriRepository{
    private var mCursor: Cursor

    init {
        val uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(android.provider.MediaStore.MediaColumns.DATA,
            android.provider.MediaStore.Images.Media.BUCKET_DISPLAY_NAME)
        mCursor = context.contentResolver.query(uri, projection, null, null, null) as Cursor
    }

    override fun getImageUri(): MutableList<String> {
        val columnIndexData = mCursor.getColumnIndex(android.provider.MediaStore.MediaColumns.DATA)
        mCursor.moveToFirst()
        val result = mutableListOf<String>()
        while (mCursor.moveToNext()) {
            val path= mCursor.getString(columnIndexData)
            result.add(path)
        }
        result.reverse()
        return result
    }
}