package com.example.advancedandroid.model

import android.content.Context
import com.example.advancedandroid.other.crawler.ListSongCrawler
import com.example.advancedandroid.other.crawler.NhacCuaTuiCrawler
import com.example.advancedandroid.other.crawler.NhacVNCrawler


interface SongRepository {
    fun getAll() : List<Song>
    fun insert(song: Song): Long
    fun findByName(name: String, limit: Int = 3): List<Song>
}

class DeviceSongRepository(private val context: Context): SongRepository {
    override fun getAll(): List<Song> {
        return SongRoomDatabase.getDatabase(context)
            .SongRoomDAO().selectAll()
    }

    override fun insert(song: Song): Long {
        return SongRoomDatabase.getDatabase(context)
            .SongRoomDAO().insert(song)
    }

    override fun findByName(name: String, limit: Int): List<Song> {
        return SongRoomDatabase.getDatabase(context)
            .SongRoomDAO().findByName(name, limit)
    }
}

class OnlineSongRepository(): SongRepository {
    val mCrawlers = mutableListOf<ListSongCrawler>()

    init {
        mCrawlers.add(NhacCuaTuiCrawler.ListSong())
        mCrawlers.add(NhacVNCrawler.ListSong())
    }

    override fun getAll(): List<Song> {
        return mutableListOf()
    }

    override fun insert(song: Song): Long {
        return -1
    }

    override fun findByName(name: String, limit: Int): List<Song> {
        val result = mutableListOf<Song>()
        val subResult = mutableListOf<MutableList<Song>>()

        var maxItem = 0
        for (crawler in mCrawlers) {
            subResult.add(crawler.search(name, limit / mCrawlers.size + 1).toMutableList())
            maxItem = Math.max(maxItem, subResult.last().size)
        }

        for (i in 0 until maxItem) {
            for (sub in subResult) {
                if (i < sub.size) {
                    result.add(sub[i])
                }
            }
        }

        return result
    }

}