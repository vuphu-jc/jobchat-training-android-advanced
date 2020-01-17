package com.example.advancedandroid.ui.songdetail

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class SongDetailViewPagerAdapter(fm: FragmentManager): FragmentStatePagerAdapter(fm) {

    companion object {
        private const val NUM_PAGES = 3
    }

    private var mSongDetailFragment = SongDetailFragment()
    private var mLyricsFragment = LyricsFragment()
    private var mChordsFragment = ChordsFragment()
    private val mFragments = HashMap<Int, Fragment>()

    init {
        mFragments.put(0, mSongDetailFragment)
        mFragments.put(1, mLyricsFragment)
        mFragments.put(2, mChordsFragment)
    }

    override fun getItem(position: Int): Fragment {
        val fragment = mFragments[position]
        if (fragment != null)
            return fragment
        return SongDetailFragment()
    }

    override fun getCount() = NUM_PAGES

}