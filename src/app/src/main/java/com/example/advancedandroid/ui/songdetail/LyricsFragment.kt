package com.example.advancedandroid.ui.songdetail


import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide

import com.example.advancedandroid.R
import com.example.advancedandroid.other.crawler.NhacCuaTuiCrawler
import com.example.advancedandroid.other.extension.gone
import com.example.advancedandroid.other.extension.visible
import com.example.advancedandroid.other.SoundManager
import kotlinx.android.synthetic.main.fragment_lyrics.artistTextView
import kotlinx.android.synthetic.main.fragment_lyrics.contentTextView
import kotlinx.android.synthetic.main.fragment_lyrics.nameTextView
import kotlinx.android.synthetic.main.fragment_lyrics.searchImageView

/**
 * A simple [Fragment] subclass.
 */
open class LyricsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lyrics, container, false)
    }

    inner class LOAD: AsyncTask<String, Unit, String>() {
        override fun doInBackground(vararg params: String?): String {
            val url = params[0] ?: return ""
            val crawler = NhacCuaTuiCrawler.ListSong()
            val urls = crawler.search(url)
            if (urls.isNotEmpty()) {
                return NhacCuaTuiCrawler.Lyrics().getContent(urls[0].url)
            }
            return ""
        }

        override fun onPostExecute(result: String?) {
            contentTextView.setText(result)
            searchImageView.gone()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        SoundManager.currentSong.observe(this, Observer {
            nameTextView.text = it.name
            artistTextView.text = it.artist

            if (it.lyrics.isEmpty()) {
                searchImageView.visible()
                searchImageView.setImageDrawable(resources.getDrawable(R.drawable.ic_add_box))
                searchImageView.setOnClickListener {_ ->
                    Glide.with(view.context).load(R.drawable.ic_loading_gif).into(searchImageView)
                    LOAD().execute(it.name)
                }
            } else {
                contentTextView.setText(it.lyrics)
                searchImageView.gone()
            }
        })
    }
}
