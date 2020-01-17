package com.example.advancedandroid.ui

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.advancedandroid.R
import com.example.advancedandroid.other.SoundManager
import com.example.advancedandroid.utils.TaskPipeline
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        SoundManager.initialize(this)
        initialize()
    }

    private fun initialize() {
        playTextView.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }

        listTextView.setOnClickListener {
            val intent = Intent(this, ListSongActivity::class.java)
            startActivity(intent)
        }
    }
}
