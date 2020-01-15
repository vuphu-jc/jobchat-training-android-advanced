package com.example.advancedandroid.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.advancedandroid.other.ForegroundSoundService
import com.example.advancedandroid.R
import com.example.advancedandroid.Utils
import com.example.advancedandroid.model.Song
import com.example.advancedandroid.other.SoundManager

class SongRecyclerViewAdapter(private val context: Context, private val data: List<Song>): RecyclerView.Adapter<SongRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.item_song, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var layout: ViewGroup = itemView.findViewById(R.id.layoutConstraintLayout)
        private var nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        private var imageImageView: ImageView = itemView.findViewById(R.id.imageImageView)
        private var artistTextView: TextView = itemView.findViewById(R.id.artistTextView)

        fun bind(position: Int) {
            val song = data[position]
            nameTextView.text = song.name
            artistTextView.text = song.artist

            layout.setOnClickListener {
            }

            Utils.LoadImageFromSong {
                Glide.with(context).load(it)
                    .error(R.drawable.ic_music_video_gray_24dp)
                    .into(imageImageView)
            }.execute(song.uri)

            layout.setOnClickListener {
                SoundManager.setPlaylist(data, position)
                SoundManager.play()
            }
        }
    }
}