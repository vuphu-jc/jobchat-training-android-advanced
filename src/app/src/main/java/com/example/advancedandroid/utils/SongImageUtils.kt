package com.example.advancedandroid.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.media.MediaMetadataRetriever
import android.os.AsyncTask
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.advancedandroid.model.Song
import java.io.File
import java.lang.Exception

object SongImageUtils {
    fun setSongImage(context: Context, song: Song, imageView: ImageView) {
        if (song.imageUrl != "") {
            Glide.with(context)
                .load(song.imageUrl)
                .into(imageView)
        } else if (song.localUri != "") {
            AsyncTaskUtils.LoadImageFromSong {
                imageView.setImageBitmap(it)
            }.execute(song.localUri)
        }
    }

    private const val MAX_RADIUS = 300

    fun setCircleSongImage(context: Context, song: Song, imageView: ImageView) {
        val requestOptions = RequestOptions()
        RequestOptions.circleCropTransform()
        requestOptions.transforms(RoundedCorners(MAX_RADIUS))

        if (song.imageUrl != "") {
            Glide.with(context)
                .load(song.imageUrl)
                .apply(requestOptions)
                .into(imageView)
        } else if (song.localUri != "") {
            AsyncTaskUtils.LoadImageFromSong {
                Glide.with(context)
                    .load(it)
                    .apply(requestOptions)
                    .into(imageView)
            }.execute(song.localUri)
        }
    }

    fun getSongImageWithCallback(context: Context, song: Song, callback: (Bitmap?) -> Unit) {
        if (song.imageUrl != "") {
            Glide.with(context)
                .asBitmap()
                .load(song.imageUrl)
                .into(object: CustomTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        callback.invoke(resource)
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                })
        }
        else if (song.localUri != "") {
            AsyncTaskUtils.LoadImageFromSong {
                callback.invoke(it)
            }.execute(song.localUri)
        }
    }
}