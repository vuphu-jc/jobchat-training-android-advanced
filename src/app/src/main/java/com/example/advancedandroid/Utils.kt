package com.example.advancedandroid

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.AsyncTask
import android.widget.ImageView
import java.io.IOException
import java.io.InputStream
import java.lang.Exception


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
}