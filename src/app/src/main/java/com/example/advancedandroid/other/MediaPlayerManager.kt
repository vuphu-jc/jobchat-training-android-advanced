package com.example.advancedandroid.other

import android.content.Context
import android.media.MediaMetadata
import android.media.MediaPlayer
import android.media.session.PlaybackState
import android.os.SystemClock
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.PlaybackStateCompat
import com.example.advancedandroid.model.Song


class MediaPlayerManager() {

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

    fun start(uri: String) {
        mMediaPlayer.finish()
        mMediaPlayer = MediaPlayer().apply {
            setOnCompletionListener {
                SoundManager.next()
            }
        }
        mMediaPlayer.initialize(uri)
        mMediaPlayer.start()

        setState(PlaybackStateCompat.STATE_PLAYING)
    }

    fun pause() {
        mMediaPlayer.pause()
        setState(PlaybackStateCompat.STATE_PAUSED)
    }

    fun replay() {
        mMediaPlayer.start()
        setState(PlaybackStateCompat.STATE_PLAYING)
    }

    fun stop() {
        setState(PlaybackState.STATE_STOPPED)
        mMediaPlayer.finish()
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