package com.example.advancedandroid.ui

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.advancedandroid.R
import com.example.advancedandroid.other.extension.getValue
import com.example.advancedandroid.model.DeviceSongRepository
import com.example.advancedandroid.model.Song
import com.example.advancedandroid.model.SongRepository
import com.example.advancedandroid.ui.adapter.SongRecyclerViewAdapter
import com.example.advancedandroid.utils.AsyncTaskUtils
import com.example.advancedandroid.utils.Constants
import com.example.advancedandroid.utils.FileUtils
import com.example.advancedandroid.utils.MediaMetadataUtils
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

        checkPermission()
    }

    private fun checkPermission() {
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

    private fun initialize() {
        AsyncTaskUtils.SimpleAsyncTask({
            mSongRepository = DeviceSongRepository(this)
            mSongAdapter =
                SongRecyclerViewAdapter(
                    this,
                    mSongRepository.getAll()
                )      // presenter
        }, {
            songsRecyclerView.adapter = mSongAdapter
            songsRecyclerView.layoutManager = LinearLayoutManager(this)
        }).execute()
    }

    private fun scanFromLocal(root: String) {        // presenter
        val allPath = FileUtils.getAllFiles(Constants.MP3_FILE_TYPE, root)
        val fromDevice = mSongRepository.getAll()
        val keys = HashSet<String>()
        fromDevice.forEach {
            keys.add(it.localUri)
        }

        var counter = 0
        allPath.forEach {
            if (!keys.contains(it)) {
                val res = MediaMetadataUtils.getMetadata(
                    it,
                    arrayListOf(MediaMetadataUtils.TITLE, MediaMetadataUtils.ARTIST))
                val name = res.getValue(MediaMetadataUtils.TITLE)
                val artist = res.getValue(MediaMetadataUtils.ARTIST)
                val song = Song(name = name, artist = artist, localUri = it)
                mSongRepository.insert(song)
                keys.add(it)
                counter++
            }
        }

        runOnUiThread {
            Toast.makeText(baseContext, "TÌM THẤY $counter BÀI HÁT", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun scanMusic() {
        AsyncTaskUtils.SimpleAsyncTask({
            scanFromLocal(Constants.ZING_MP3_PATH)
        }, {
            initialize()
        }).execute()
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.scanMusic -> {
                scanMusic()
                true
            }
            else ->
                super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.list_song_menu, menu)
        return true
    }


}
