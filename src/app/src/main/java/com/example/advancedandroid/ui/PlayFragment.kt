package com.example.advancedandroid.ui


import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.advancedandroid.R
import com.example.advancedandroid.other.extension.gone
import com.example.advancedandroid.other.extension.visible
import com.example.advancedandroid.other.SoundManager
import com.example.advancedandroid.ui.songdetail.SongDetailActivity
import com.example.advancedandroid.utils.AsyncTaskUtils
import com.example.advancedandroid.utils.SongImageUtils
import kotlinx.android.synthetic.main.fragment_play.*

/**
 * A simple [Fragment] subclass.
 */
class PlayFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_play, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        SoundManager.currentSong.observe(this, Observer {
            if (it == null) {
                layoutConstraintLayout.gone()
            } else {
                layoutConstraintLayout.visible()
                nameTextView.text = it.name
                artistTextView.text = it.artist
                SongImageUtils.setSongImage(view.context, it, imageImageView)
            }
        })


        SoundManager.isPlaying.observe(this, Observer {
            if (it) {
                playImageView.setImageResource(R.drawable.ic_pause_black_36dp)
            } else
                playImageView.setImageResource(R.drawable.ic_play_arrow_black_36dp)
        })

        playImageView.setOnClickListener {
            SoundManager.pauseOrReplay()
        }

        nextImageView.setOnClickListener {
            SoundManager.next()
        }

        layoutConstraintLayout.setOnClickListener {
            if (activity !is SongDetailActivity) {
                val intent = Intent(view.context, SongDetailActivity::class.java)
                startActivity(intent)
            }
        }
    }
}
