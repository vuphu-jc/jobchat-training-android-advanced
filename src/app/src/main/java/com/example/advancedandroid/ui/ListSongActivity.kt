package com.example.advancedandroid.ui

import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.advancedandroid.R
import com.example.advancedandroid.model.DeviceSongRepository
import com.example.advancedandroid.model.Song
import com.example.advancedandroid.model.SongRepository
import kotlinx.android.synthetic.main.activity_list_song.*

class ListSongActivity : AppCompatActivity() {

    private lateinit var mSongAdapter: SongRecyclerViewAdapter
    private var mSongs = mutableListOf<Song>()
    private lateinit var mSongRepository: SongRepository

    companion object {
        private const val ACCESS_PERMISSION_REQUEST_CODE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_song)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                checkSelfPermission(android.Manifest.permission.FOREGROUND_SERVICE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(
                    arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        android.Manifest.permission.FOREGROUND_SERVICE),
                    ACCESS_PERMISSION_REQUEST_CODE
                )
            } else
                initialize()
        } else
            initialize()
    }

    private fun checkPermission() {
    }

    private fun initialize() {
        mSongRepository =
            DeviceSongRepository()
        mSongAdapter = SongRecyclerViewAdapter(
            this,
            mSongRepository.getAll()
        )
        songsRecyclerView.adapter = mSongAdapter
        songsRecyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == ACCESS_PERMISSION_REQUEST_CODE &&
                grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            initialize()
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}
