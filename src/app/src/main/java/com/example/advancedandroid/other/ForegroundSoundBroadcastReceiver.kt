package com.example.advancedandroid.other

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.view.KeyEvent

class ForegroundSoundBroadcastReceiver: BroadcastReceiver() {

    companion object {
        const val PREVIOUS_BROADCAST = "com.jobchat.training.musicapp.PREVIOUS"
        const val NEXT_BROADCAST = "com.jobchat.training.musicapp.NEXT"
        const val PAUSE_REPLAY_BROADCAST = "com.jobchat.training.musicapp.PAUSE_REPLAY"
        const val CLOSE_BROADCAST = "com.jobchat.training.musicapp.CLOSE"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        if (context == null || intent == null)
            return

        when (intent.action) {
            PREVIOUS_BROADCAST -> {
                SoundManager.previous()
            }
            NEXT_BROADCAST -> {
                SoundManager.next()
            }
            PAUSE_REPLAY_BROADCAST -> {
                SoundManager.pauseOrReplay()
            }
            CLOSE_BROADCAST -> {
                SoundManager.close()
            }
        }
    }
}