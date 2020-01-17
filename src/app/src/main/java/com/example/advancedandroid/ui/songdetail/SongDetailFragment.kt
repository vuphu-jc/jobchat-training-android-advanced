package com.example.advancedandroid.ui.songdetail


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.lifecycle.Observer

import com.example.advancedandroid.R
import com.example.advancedandroid.utils.SongImageUtils
import com.example.advancedandroid.other.SoundManager
import kotlinx.android.synthetic.main.fragment_song_detail.*

/**
 * A simple [Fragment] subclass.
 */
class SongDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_song_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize(view)
    }

    private fun setEvent() {
        previousImageView.setOnClickListener {
            SoundManager.previous()
        }

        nextImageView.setOnClickListener {
            SoundManager.next()
        }

        pauseImageView.setOnClickListener {
            SoundManager.pauseOrReplay()
        }
    }

    private fun initialize(view: View) {
        SoundManager.currentSong.observe(this, Observer {
            nameTextView.text = it.name
            artistTextView.text = it.artist

            SongImageUtils.setCircleSongImage(view.context, it, imageImageView)
            SongImageUtils.setSongImage(view.context, it, backgroundImageView)

            val rotation = AnimationUtils.loadAnimation(view.context, R.anim.rotate_continuously);
            imageImageView.startAnimation(rotation)
        })

        SoundManager.isPlaying.observe(this, Observer {
            if (it) {
                pauseImageView.setImageResource(R.drawable.ic_pause_black_36dp)
            } else
                pauseImageView.setImageResource(R.drawable.ic_play_arrow_black_36dp)
        })

        setEvent()
    }
}
