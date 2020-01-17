package com.example.advancedandroid.other

import android.content.Context
import android.media.AudioManager
import android.media.MediaMetadata
import android.media.MediaPlayer
import android.media.session.PlaybackState
import android.net.Uri
import android.os.SystemClock
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.PlaybackStateCompat
import androidx.lifecycle.MutableLiveData
import com.example.advancedandroid.model.Song
import java.lang.Exception


object MediaPlayerManager {

    private var mMediaPlayer = MediaPlayer()

    private var mPlaybackStateBuilder = PlaybackStateCompat.Builder().apply {
        setActions(PlaybackStateCompat.ACTION_SEEK_TO)
    }

    private var mCurrentState = PlaybackStateCompat.STATE_PLAYING

    private fun MediaPlayer.initialize(uri: String) {
        setDataSource(uri)
        prepare()
    }

    private fun MediaPlayer.finish() {
        stop()
        release()
    }

    fun start(song: Song): Boolean {
        mMediaPlayer.finish()
        mMediaPlayer = MediaPlayer().apply {
            setOnCompletionListener {
                SoundManager.next()
            }
        }

        try {
            if (song.localUri != "") {
                mMediaPlayer.initialize(song.localUri)
                mMediaPlayer.start()
            } else if (song.onlineUri != "") {
                mMediaPlayer = MediaPlayer.create(SoundManager.mContext, Uri.parse(song.onlineUri))
                mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
            } else
                return false
        } catch (e: Exception) {
            return false
        }
        setState(PlaybackStateCompat.STATE_PLAYING)
        SoundManager.isPlaying.value = true
        return true
    }

    fun pause() {
        mMediaPlayer.pause()
        setState(PlaybackStateCompat.STATE_PAUSED)
        SoundManager.isPlaying.setValue(false)
    }

    fun replay() {
        mMediaPlayer.start()
        setState(PlaybackStateCompat.STATE_PLAYING)
        SoundManager.isPlaying.setValue(true)
    }

    fun stop() {
        setState(PlaybackState.STATE_STOPPED)
        mMediaPlayer.finish()
        SoundManager.isPlaying.setValue(false)
    }

    fun seekTo(position: Long) {
        mMediaPlayer.seekTo(position.toInt())
    }

    fun getPlaybackState(): PlaybackStateCompat {
        mPlaybackStateBuilder.setState(mCurrentState, getCurrentPosition(), 1.0f, SystemClock.elapsedRealtime())
        return mPlaybackStateBuilder.build()
    }

    fun getMetadata(): MediaMetadataCompat {
        val builder = MediaMetadataCompat.Builder()
        builder.putLong(MediaMetadataCompat.METADATA_KEY_DURATION, mMediaPlayer.duration.toLong())
        return builder.build()
    }

    private fun getCurrentPosition() = mMediaPlayer.currentPosition.toLong()

    fun isPlaying() = mMediaPlayer.isPlaying

    private fun setState(state: Int)
    {
        mCurrentState = state
    }
}