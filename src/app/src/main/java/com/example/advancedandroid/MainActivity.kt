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
//        btnAddPadding.setOnClickListener {
//            var start = 0
//            var finish = 100
//            if (view_pager.paddingLeft > 0) {
//                start = 100
//                finish = 0
//            }
//
//            val valueAnimator = ValueAnimator.ofInt(start, finish)
//            valueAnimator.duration = 500
//            valueAnimator.addUpdateListener {
//                val value = it.animatedValue as Int
//                view_pager.setPadding(value, 0, value, value)
//            }
//            valueAnimator.start()
//        }
    }
}
