package com.example.advancedandroid.model

import android.media.MediaMetadataRetriever
import com.example.advancedandroid.Utils


interface SongRepository {
    fun getAll() : List<Song>
}

class DeviceSongRepository: SongRepository {

    companion object {
        private const val FILE_TYPE: String = ".mp3"
        private const val ZING_MP3_PATH: String = "/storage/emulated/0/Zing MP3/"
        private const val UNKNOWN: String = "Unknown"
    }

    private fun MediaMetadataRetriever.get(keyCode: Int): String {
        var value = this.extractMetadata(keyCode)
        if (value == null) value =
            UNKNOWN
        return value
    }
    
    private fun MediaMetadataRetriever.fillData(uri: String): Song {
        setDataSource(uri)
        val name = get(MediaMetadataRetriever.METADATA_KEY_TITLE)
        val artist = get(MediaMetadataRetriever.METADATA_KEY_ARTIST)
        
        return Song(name, artist, uri)
    }

    override fun getAll(): List<Song> {
        var allPath = Utils.getAllFiles(
            FILE_TYPE,
            ZING_MP3_PATH
        )
        val result = mutableListOf<Song>()
        for (uri in allPath)
            result.add(MediaMetadataRetriever().fillData(uri))
        return result
    }
}