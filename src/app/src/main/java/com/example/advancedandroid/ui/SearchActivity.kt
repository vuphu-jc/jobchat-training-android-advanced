package com.example.advancedandroid.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.advancedandroid.R
import com.example.advancedandroid.model.DeviceSongRepository
import com.example.advancedandroid.model.OnlineSongRepository
import com.example.advancedandroid.model.Song
import com.example.advancedandroid.model.SongURL
import com.example.advancedandroid.other.crawler.HopAmChuanCrawler
import com.example.advancedandroid.other.crawler.NhacCuaTuiCrawler
import com.example.advancedandroid.other.crawler.NhacVNCrawler
import com.example.advancedandroid.ui.adapter.*
import com.example.advancedandroid.utils.ActivityUtils
import com.example.advancedandroid.utils.AsyncTaskUtils
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity() {

    val mOnlineRepository = OnlineSongRepository()
    val mDeviceRepository = DeviceSongRepository(this)
    val data = mutableListOf<CustomDataRecyclerView>()
    val mRecyclerViewAdapter = CustomRecyclerViewAdapter(this, data)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        initialize()
    }

    var mAsyncTask: AsyncTaskUtils.SimpleAsyncTask? = null

    fun notifyDataSetChanged() {
        runOnUiThread {
            songsRecyclerView.adapter = CustomRecyclerViewAdapter(this, data)
        }
    }

    private fun search(keywords: String) {
        if (mAsyncTask != null) {
            mAsyncTask?.cancel(true)
        }

        data.clear()
        data.add(LoadingDataRecyclerView())
        notifyDataSetChanged()

         mAsyncTask = AsyncTaskUtils.SimpleAsyncTask({
            val deviceSongs = mDeviceRepository.findByName(keywords)
            data.add(data.size - 1, TitleDataRecyclerView(getString(R.string.on_deivce_title)))
            for (song in deviceSongs) {
                data.add(data.size - 1, SongDataRecyclerView(song))
            }
             notifyDataSetChanged()
             if (ActivityUtils.isNetworkConnected(this)) {
                 val onlineSongs = mOnlineRepository.findByName(keywords)
                 data.add(data.size - 1, TitleDataRecyclerView(getString(R.string.online_title)))
                 for (song in onlineSongs) {
                     data.add(data.size - 1, SongDataRecyclerView(song))
                 }
             }
        }, {
             data.removeAt(data.size - 1)
             notifyDataSetChanged()
        })

        mAsyncTask?.execute()
    }

    private fun initialize() {
        searchImageView.setOnClickListener {
            ActivityUtils.hideKeyboard(this)
            search(searchContentEditText.text.toString())
        }

        songsRecyclerView.layoutManager = LinearLayoutManager(this)
    }
}
