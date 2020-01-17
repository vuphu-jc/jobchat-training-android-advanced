package com.example.advancedandroid.utils

import android.annotation.SuppressLint
import android.media.MediaMetadata
import android.media.MediaMetadataRetriever
import com.example.advancedandroid.model.Song

object MediaMetadataUtils {
    const val TITLE: Int = MediaMetadataRetriever.METADATA_KEY_TITLE
    const val ARTIST: Int = MediaMetadataRetriever.METADATA_KEY_ARTIST

    private fun MediaMetadataRetriever.get(keyCode: Int): String {
        var value = this.extractMetadata(keyCode)
        if (value == null)
            value = ""
        return value
    }

    @SuppressLint("UseSparseArrays")
    fun getMetadata(uri: String, keys: List<Int>): HashMap<Int,String> {
        val retriever = MediaMetadataRetriever()
        retriever.setDataSource(uri)
        val result = HashMap<Int,String>()
        for (key in keys)
            result[key] = retriever.get(key)
        return result
    }
}