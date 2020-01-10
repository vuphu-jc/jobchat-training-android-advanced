package com.example.advancedandroid

import android.animation.ValueAnimator
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initialize()
    }

    private fun initialize() {
        val adapter = SuperHeroInformationFragmentAdapter(this, supportFragmentManager, MockSuperHeroRepository(this))
        view_pager.adapter = adapter
        tabs.setupWithViewPager(view_pager)

        adapter.initializeTab(tabs) {
                prev: String, curr: String, next: String ->
                    tvPrevious.text = prev
                    tvCurrent.text = curr
                    tvNext.text = next
        }
    }
}
