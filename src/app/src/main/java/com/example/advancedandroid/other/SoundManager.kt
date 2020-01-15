package com.example.advancedandroid.other

import android.content.Context
import com.example.advancedandroid.model.Song

object SoundManager {
    lateinit var context: Context
    lateinit var mPlaylist: List<Song>
    var mIndex: Int = 0

    fun initialize(context: Context) {
        SoundManager.context = context
    }

    fun setPlaylist(playlist: List<Song>, index: Int = 0) {
        mPlaylist = playlist
        mIndex = index
    }

    fun play() {
        ForegroundSoundService.start(context, mPlaylist[mIndex])
    }

    fun next() {
        mIndex = (mIndex + 1) % mPlaylist.size
        play()
    }

    fun previous() {
        mIndex = mIndex - 1 + mPlaylist.size
        play()
    }

    fun pauseOrReplay() {
        ForegroundSoundService.pauseOrReplay(context)
    }

    fun close() {
        ForegroundSoundService.stop(context)
    }
}