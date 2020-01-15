package com.example.advancedandroid.other

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v4.media.session.MediaSessionCompat
import androidx.core.app.NotificationCompat
import com.example.advancedandroid.R
import com.example.advancedandroid.Utils
import com.example.advancedandroid.model.Song
import com.example.advancedandroid.ui.MainActivity


class CustomNotification(private val context: Context) {

    companion object {
        private const val CHANNEL_ID = "JOBCHAT"
        private const val CHANNEL_NAME = "PLAY_MUSIC"
        private const val CHANNEL_DESCRIPTION = "THIS IS NOTIFICATION"

        private const val ACTION_PREVIOUS_DESCRIPTION = "PLAY"
        private const val ACTION_PREVIOUS_INDEX = 0
        private const val ACTION_PAUSE_DESCRIPTION = "PAUSE"
        private const val ACTION_REPLAY_DESCRIPTION = "REPLAY"
        private const val ACTION_PAUSE_REPLAY_INDEX = 1
        private const val ACTION_NEXT_DESCRIPTION = "PLAY"
        private const val ACTION_NEXT_INDEX = 2
        private const val ACTION_CLOSE_DESCRIPTION = "CLOSE"
        private const val ACTION_CLOSE_INDEX = 3

    }

    private lateinit var mBaseBuilder: NotificationCompat.Builder
    private lateinit var mSong: Song
    private var isLoadedImage = false
    init {
        createBaseNotification()
    }

    private fun createBaseNotification() {
        val notificationIntent = Intent(context, MainActivity::class.java)
        notificationIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        val intent = PendingIntent.getActivity(context, 0, notificationIntent, 0)

        val createPendingIntent = fun(action: String): PendingIntent {
            val intent = Intent(context, ForegroundSoundBroadcastReceiver::class.java).setAction(action)
            return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        }

        val prevPendingIntent = createPendingIntent(ForegroundSoundBroadcastReceiver.PREVIOUS_BROADCAST)
        val nextPendingIntent = createPendingIntent(ForegroundSoundBroadcastReceiver.NEXT_BROADCAST)
        val closePendingIntent = createPendingIntent(ForegroundSoundBroadcastReceiver.CLOSE_BROADCAST)

        mBaseBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setSmallIcon(R.drawable.ic_music_video_gray_24dp)
            .setShowWhen(false)
            .setContentIntent(intent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .addAction(R.drawable.ic_skip_previous_black_36dp, ACTION_PREVIOUS_DESCRIPTION, prevPendingIntent)
            .addAction(R.drawable.ic_play_arrow_black_36dp, ACTION_PAUSE_DESCRIPTION, null)
            .addAction(R.drawable.ic_skip_next_black_36dp, ACTION_NEXT_DESCRIPTION, nextPendingIntent)
            .addAction(R.drawable.ic_close_black_24dp, ACTION_CLOSE_DESCRIPTION, closePendingIntent)

        createChannel()
    }

    fun applyMetadata(song: Song) : CustomNotification {
        mBaseBuilder
            .setContentTitle(song.name)
            .setContentText(song.artist)
        mSong = song
        isLoadedImage = false
        return this
    }

    fun applyMediaSession(mediaSession: MediaSessionCompat) : CustomNotification {
        mBaseBuilder
            .setStyle(androidx.media.app.NotificationCompat.MediaStyle()
            .setMediaSession(mediaSession.sessionToken)
            .setShowActionsInCompactView(
                ACTION_PREVIOUS_INDEX,
                ACTION_PAUSE_REPLAY_INDEX,
                ACTION_NEXT_INDEX))

        return this
    }

    fun pauseNotification() : CustomNotification {
        val createPendingIntent = fun(action: String): PendingIntent {
            val intent = Intent(context, ForegroundSoundBroadcastReceiver::class.java).setAction(action)
            return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        }

        val pauseReplayPendingIntent = createPendingIntent(ForegroundSoundBroadcastReceiver.PAUSE_REPLAY_BROADCAST)

        mBaseBuilder.mActions[ACTION_PAUSE_REPLAY_INDEX] =
            NotificationCompat.Action(R.drawable.ic_pause_black_36dp, ACTION_PAUSE_DESCRIPTION, pauseReplayPendingIntent)

        return this
    }

    fun replayNotification() : CustomNotification {
        val createPendingIntent = fun(action: String): PendingIntent {
            val intent = Intent(context, ForegroundSoundBroadcastReceiver::class.java).setAction(action)
            return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        }

        val pauseReplayPendingIntent = createPendingIntent(ForegroundSoundBroadcastReceiver.PAUSE_REPLAY_BROADCAST)

        mBaseBuilder.mActions[ACTION_PAUSE_REPLAY_INDEX] =
            NotificationCompat.Action(R.drawable.ic_play_arrow_black_36dp, ACTION_REPLAY_DESCRIPTION, pauseReplayPendingIntent)

        return this
    }

    fun build(callback: (Notification) -> Unit) {
        if (isLoadedImage)
            callback.invoke(mBaseBuilder.build())
        else {
            Utils.LoadImageFromSong {
                mBaseBuilder.setLargeIcon(it)
                isLoadedImage = true
                callback.invoke(mBaseBuilder.build())
            }.execute(mSong.uri)
        }
    }

    // In case of version >= Android O
    private fun createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_LOW).apply {
                this.description = CHANNEL_DESCRIPTION
                this.lockscreenVisibility = NotificationCompat.VISIBILITY_PUBLIC
            }
            val notificationManager =
                context.getSystemService(
                    NotificationManager::class.java
                )
            notificationManager.createNotificationChannel(channel)
        }
    }
}