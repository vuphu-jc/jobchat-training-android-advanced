package com.example.advancedandroid.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.advancedandroid.R
import com.example.advancedandroid.model.Song
import com.example.advancedandroid.other.SoundManager
import com.example.advancedandroid.other.factory.LogoFactory
import com.example.advancedandroid.ui.SearchActivity
import com.example.advancedandroid.ui.songdetail.SongDetailActivity
import com.example.advancedandroid.utils.SongImageUtils

class TitleDataRecyclerView(title: String): CustomDataRecyclerView(title) {
    override fun createViewHolder(context: Context, parent: ViewGroup): CustomViewHolderRecyclerView {
        val view = LayoutInflater.from(context).inflate(R.layout.item_title, parent, false)
        return ViewHolder(view)
    }

    class ViewHolder(itemView: View): CustomViewHolderRecyclerView(itemView) {
        val titleTextView = itemView.findViewById<TextView>(R.id.titleTextView)

        override fun bind(customData: Any?) {
            if (customData is String) {
                titleTextView.text = customData
            }
        }
    }
}

class LoadingDataRecyclerView: CustomDataRecyclerView(null) {
    override fun createViewHolder(
        context: Context,
        parent: ViewGroup
    ): CustomViewHolderRecyclerView {
        val view = LayoutInflater.from(context).inflate(R.layout.item_loading, parent, false)
        return ViewHolder(context, view)
    }

    class ViewHolder(val context: Context, itemView: View): CustomViewHolderRecyclerView(itemView) {
        val loadingImageView = itemView.findViewById<ImageView>(R.id.loadingImageView)

        override fun bind(customData: Any?) {
            Glide.with(context)
                .load(R.drawable.ic_loading_gif_1)
                .into(loadingImageView)
        }
    }
}


class SongDataRecyclerView(song: Song): CustomDataRecyclerView(song) {
    override fun createViewHolder(
        context: Context,
        parent: ViewGroup
    ): CustomViewHolderRecyclerView {
        val v = LayoutInflater.from(context).inflate(R.layout.item_search_song, parent, false)
        return ViewHolder(context, v)
    }

    class ViewHolder(val context: Context, itemView: View): CustomViewHolderRecyclerView(itemView) {

        private var layout: ViewGroup = itemView.findViewById(R.id.layoutConstraintLayout)
        private var nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        private var imageImageView: ImageView = itemView.findViewById(R.id.imageImageView)
        private var artistTextView: TextView = itemView.findViewById(R.id.artistTextView)
        private var sourceImageView: ImageView = itemView.findViewById(R.id.sourceImageView)

        override fun bind(song: Any?) {
            if (song is Song) {
                sourceImageView.setImageDrawable(context.resources.getDrawable(LogoFactory.getLogo(song)))
                nameTextView.text = song.name
                artistTextView.text = song.artist
                SongImageUtils.setSongImage(context, song, imageImageView)

                layout.setOnClickListener {
                    SoundManager.setPlaylist(mutableListOf(song))
                    SoundManager.play()

                    val intent = Intent(context.applicationContext, SongDetailActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent)

                    if (context is SearchActivity) {
                        context.finish()
                    }
                }
            }
        }
    }
}
