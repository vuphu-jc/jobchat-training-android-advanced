package com.example.advancedandroid.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.os.AsyncTask

object AsyncTaskUtils {
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

    class SimpleAsyncTask
        (val onBackground: ()->Unit,
         val onUIThread: () -> Unit)
        : AsyncTask<Unit, Unit, Unit>() {
        override fun doInBackground(vararg params: Unit?) {
            onBackground()
        }

        override fun onPostExecute(result: Unit?) {
            onUIThread()
        }
    }
}