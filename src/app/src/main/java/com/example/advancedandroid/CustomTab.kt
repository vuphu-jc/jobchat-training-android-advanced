package com.example.advancedandroid

import android.content.Context
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.custom_tab_layout.view.*

class CustomTab(context: Context) : FrameLayout(context) {

    init {
        inflate(context, R.layout.custom_tab_layout, this)
    }

    fun setFirstLetter(letter: Char) {
        tvFirstLetter.text = letter.toString()
    }

    fun ImageView.setColor(resId: Int) {
        this.setColorFilter(context.resources.getColor(resId))
    }

    fun selected() {
        imgCircle.setColor(R.color.colorTabLayoutIconSelected)
    }

    fun unselected() {
        imgCircle.setColor(R.color.colorTabLayoutIcon)
    }
}