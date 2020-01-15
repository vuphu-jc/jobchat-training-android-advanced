package com.example.advancedandroid

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.os.AsyncTask
import android.os.Message
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import java.io.ByteArrayInputStream
import java.io.File
import java.lang.Exception

object Utils {
    private fun getAllFilesRecursion(type: String, path: String): List<String> {
        try {
            var result = mutableListOf<String>()
            val rootFolder = File(path)
            val files = rootFolder.listFiles()
            for (file in files) {
                if (file.isDirectory)
                    result.addAll(getAllFilesRecursion(type, file.absolutePath))
                else if (file.name.endsWith(type))
                    result.add(file.absolutePath)
            }
            return result
        } catch (e: Exception) {
            return mutableListOf()
        }
    }

    fun getAllFiles(type: String, rootPath: String = "/storage/"): List<String> {
        return getAllFilesRecursion(type, rootPath)
    }

    class LoadImageFromSong(val invoke: (Bitmap?) -> Unit)
        : AsyncTask<String, Unit, Bitmap>() {

        private lateinit var retriever: MediaMetadataRetriever
        override fun onPreExecute() {
            super.onPreExecute()
            retriever = MediaMetadataRetriever()
        }

        override fun doInBackground(vararg params: String?) : Bitmap? {
            val uri = params[0] as String
            retriever.setDataSource(uri)
            val arr = retriever.embeddedPicture
            if (arr != null)
                return BitmapFactory.decodeByteArray(arr, 0, arr.size)
            return  null
        }

        override fun onPostExecute(result: Bitmap?) {
            invoke.invoke(result)
        }
    }
}