package com.example.advancedandroid.other

import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.MediaPlayer
import android.media.session.PlaybackState
import android.os.IBinder
import android.os.SystemClock
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.util.Log
import com.example.advancedandroid.Utils
import com.example.advancedandroid.model.Song


class ForegroundSoundService: Service() {

    companion object {
        private const val SONG = "SONG"
        private const val ACTION_PLAY = "PLAY"
        private const val ACTION_PAUSE_REPLAY = "PAUSE"

        private const val NOTIFICATION_ID = 1
        private const val MEDIA_SESSION_TAG = "MEDIA_SESSION"

        fun pauseOrReplay(context: Context) {
            val intent = Intent(context, ForegroundSoundService::class.java)
            intent.action = ACTION_PAUSE_REPLAY
            context.startService(intent)
        }

        fun stop(context: Context) {
            val intent = Intent(context, ForegroundSoundService::class.java)
            context.stopService(intent)
        }

        fun start(context: Context, song: Song) {
            val intent = Intent(context, ForegroundSoundService::class.java)
            intent.action = ACTION_PLAY
            intent.putExtra(SONG, song)
            context.startService(intent)
        }
    }

    private lateinit var mMediaPlayerManager: MediaPlayerManager
    private lateinit var mSong: Song
    private lateinit var mBroadcastReceiver: ForegroundSoundBroadcastReceiver
    private lateinit var mMediaSessionCompat: MediaSessionCompat
    private lateinit var mCustomNotification: CustomNotification

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        initializeBroadcastReceiver()
        mMediaPlayerManager = MediaPlayerManager()
        mCustomNotification = CustomNotification(baseContext)
        mMediaSessionCompat = MediaSessionCompat(baseContext, MEDIA_SESSION_TAG).apply {
            isActive = true
            setCallback(object: MediaSessionCompat.Callback() {
                override fun onSeekTo(pos: Long) {
                    super.onSeekTo(pos)
                    mMediaPlayerManager.seekTo(pos)
                    rebuildNotification()
                }
            })
        }
    }

    private fun initializeBroadcastReceiver() {
        val filter = IntentFilter()
        filter.addAction(ForegroundSoundBroadcastReceiver.PREVIOUS_BROADCAST)
        filter.addAction(ForegroundSoundBroadcastReceiver.NEXT_BROADCAST)
        filter.addAction(ForegroundSoundBroadcastReceiver.CLOSE_BROADCAST)
        filter.addAction(ForegroundSoundBroadcastReceiver.PAUSE_REPLAY_BROADCAST)
        mBroadcastReceiver = ForegroundSoundBroadcastReceiver()
        registerReceiver(mBroadcastReceiver, filter)
    }


    private fun onStart() {
        mMediaPlayerManager.start(mSong.uri)
        mMediaSessionCompat.setMetadata(mMediaPlayerManager.getMetadata())

        mCustomNotification.pauseNotification()
        rebuildNotification()
    }

    private fun onPauseOrReplay() {
        if (mMediaPlayerManager.isPlaying()) {
            mMediaPlayerManager.pause()
            mCustomNotification.replayNotification()
        } else {
            mMediaPlayerManager.replay()
            mCustomNotification.pauseNotification()
        }
        rebuildNotification()
    }

    private fun rebuildNotification() {
        mMediaSessionCompat.setPlaybackState(mMediaPlayerManager.getPlaybackState())
        mCustomNotification.applyMediaSession(mMediaSessionCompat)
        mCustomNotification.build {
            startForeground(NOTIFICATION_ID, it)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent?.action == ACTION_PLAY) {
            mSong = intent.getParcelableExtra(SONG)
            mCustomNotification.applyMetadata(mSong)
            onStart()
        } else if (intent?.action == ACTION_PAUSE_REPLAY)
            onPauseOrReplay()

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        mMediaPlayerManager.stop()
        unregisterReceiver(mBroadcastReceiver)
    }
}