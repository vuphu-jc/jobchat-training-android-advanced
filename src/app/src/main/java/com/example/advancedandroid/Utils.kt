package com.example.advancedandroid

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.util.DisplayMetrics
import android.widget.ImageView
import java.util.concurrent.Semaphore


object Utils {
    fun getDisplayMetrics(activity: Activity) : DisplayMetrics {
        val displayMetrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics
    }

    class ImageLoader private constructor(){
        companion object {
            private var sCache = HashMap<String, Bitmap>()

            fun from(context: Context) : ImageLoader {
                return ImageLoader().apply {
                    this.mContext = context
                }
            }
        }

        private lateinit var mContext: Context

        fun load(uri: String, imageView: ImageView) {
            if (sCache[uri] != null)
                imageView.setImageBitmap(sCache[uri])
            else
                LoadImageFromUri(imageView).execute(uri)
        }

        inner class LoadImageFromUri(val imageView: ImageView)
            : AsyncTask<String,Unit,Bitmap>(){
            override fun doInBackground(vararg params: String?): Bitmap {
                val uri = params[0] as String
                return BitmapFactory.decodeFile(uri)
            }

            override fun onPostExecute(result: Bitmap?) {
                imageView.setImageBitmap(result)
            }
        }
    }
}