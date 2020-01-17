package com.example.advancedandroid.ui.songdetail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.advancedandroid.R
import kotlinx.android.synthetic.main.activity_song_detail.*


class SongDetailActivity : AppCompatActivity() {

    val mViewPagerAdapter = SongDetailViewPagerAdapter(supportFragmentManager)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song_detail)

        initialize()
    }

    private fun initialize() {
        viewViewPager.adapter = mViewPagerAdapter
        viewViewPager.offscreenPageLimit = 3;
    }
}
