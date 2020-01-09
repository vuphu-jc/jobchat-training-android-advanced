package com.example.advancedandroid

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.AsyncTask
import android.widget.ImageView
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.FileNameMap


object Utils {

    class LoadImageFromAssets(private val context: Context, private val imageView: ImageView) : AsyncTask<String, Unit, Drawable>() {
        override fun doInBackground(vararg params: String?): Drawable? {
            try {
                val ims: InputStream = context.assets.open(params[0] as String)
                return Drawable.createFromStream(ims, null)
            } catch (e: Exception) {
                return null
            }
        }

        override fun onPostExecute(result: Drawable?) {
            super.onPostExecute(result)
            if (result != null) {
                imageView.setImageDrawable(result)
            }
        }
    }

    fun setSourceForImageView(context: Context, imageView: ImageView, assetsUri: String) {
        val asyncTask = LoadImageFromAssets(context, imageView)
        asyncTask.execute(assetsUri)
    }

    fun getTextFromRaw(context: Context, fileName: String) : String? {
        val ins: InputStream = context.assets.open(fileName)
        return BufferedReader(InputStreamReader(ins)).readText()
    }
}