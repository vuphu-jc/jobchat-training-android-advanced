package com.example.advancedandroid

import android.content.Context
import android.widget.FrameLayout
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.custom_tab_layout.view.*

class CustomTab(context: Context) : FrameLayout(context) {

    init {
        inflate(context, R.layout.custom_tab_layout, this)
    }

    fun setFirstLetter(letter: Char) {
        tvFirstLetter.text = letter.toString()
    }

    fun selected() {
        imgCircle.setColorFilter(context.resources.getColor(R.color.colorTabLayoutIconSelected))
    }

    fun unselected() {
        imgCircle.setColorFilter(context.resources.getColor(R.color.colorTabLayoutIcon))
    }
}