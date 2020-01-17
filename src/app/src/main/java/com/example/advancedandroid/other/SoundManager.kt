package com.example.advancedandroid.other

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.advancedandroid.model.Song

object SoundManager {
    lateinit var mContext: Context
    lateinit var mPlaylist: List<Song>
    var mIndex: Int = 0

    val currentSong = MutableLiveData<Song>()
    val isPlaying = MutableLiveData<Boolean>()

    fun initialize(context: Context) {
        SoundManager.mContext = context
        currentSong.value = null
    }

    private fun broadcastCurrentSong() {
        currentSong.value = mPlaylist[mIndex]
    }

    fun setPlaylist(playlist: List<Song>, index: Int = 0) {
        mPlaylist = playlist
        mIndex = index
    }

    fun play() {
        broadcastCurrentSong()
        ForegroundSoundService.start(mContext)
    }

    fun next() {
        mIndex = (mIndex + 1) % mPlaylist.size
        play()
    }

    fun previous() {
        mIndex = (mIndex - 1 + mPlaylist.size) % mPlaylist.size
        play()
    }

    fun pauseOrReplay() {
        ForegroundSoundService.pauseOrReplay(mContext)
    }

    fun close() {
        ForegroundSoundService.stop(mContext)
    }
}