package com.example.advancedandroid.ui.songdetail


import android.os.AsyncTask
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.BackgroundColorSpan
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide

import com.example.advancedandroid.R
import com.example.advancedandroid.other.crawler.HopAmChuanCrawler
import com.example.advancedandroid.other.extension.gone
import com.example.advancedandroid.other.extension.visible
import com.example.advancedandroid.other.SoundManager
import kotlinx.android.synthetic.main.fragment_lyrics.*

/**
 * A simple [Fragment] subclass.
 */
class ChordsFragment : LyricsFragment() {

    inner class LOAD: AsyncTask<String, Unit, String>() {
        override fun doInBackground(vararg params: String?): String {
            val url = params[0] ?: return ""
            val crawler = HopAmChuanCrawler.ListSong()
            val urls = crawler.search(url)
            if (urls.isNotEmpty()) {
                return HopAmChuanCrawler.Chords().getContent(urls[0].url)
            }
            return ""
        }

        override fun onPostExecute(result: String?) {
            val spannable = SpannableString(result)
            val regex = "\\[\\w*\\]".toRegex()
            var positions = regex.findAll(result as String)
            for (pos in positions) {
                Log.d("POO", pos.value)
                spannable.setSpan(BackgroundColorSpan(resources.getColor(R.color.colorLightBlue)), pos.range.start,
                    pos.range.last + 1, Spannable.SPAN_PRIORITY_SHIFT)
            }
            contentTextView.setText(spannable, TextView.BufferType.SPANNABLE)
            searchImageView.gone()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        titleTextView.text = context?.resources?.getString(R.string.chords_title)

        SoundManager.currentSong.observe(this, Observer {
            nameTextView.text = it.name
            artistTextView.text = it.artist

            if (it.chords.isEmpty()) {
                searchImageView.visible()
                searchImageView.setImageDrawable(resources.getDrawable(R.drawable.ic_add_box))
                searchImageView.setOnClickListener {_ ->
                    Glide.with(view.context).load(R.drawable.ic_loading_gif).into(searchImageView)
                    LOAD().execute(it.name)
                }
            } else {
                contentTextView.setText(it.chords)
                searchImageView.gone()
            }
        })
    }
}
