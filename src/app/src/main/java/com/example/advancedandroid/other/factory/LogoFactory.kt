package com.example.advancedandroid.other.factory

import com.example.advancedandroid.R
import com.example.advancedandroid.model.Song
import com.example.advancedandroid.other.crawler.HopAmChuanCrawler
import com.example.advancedandroid.other.crawler.NhacCuaTuiCrawler
import com.example.advancedandroid.other.crawler.NhacVNCrawler

object LogoFactory {
    fun getLogo(song: Song): Int {
        if (song.url.isEmpty())
            return R.drawable.ic_file_download_black_24dp
        if (song.url.contains(HopAmChuanCrawler.SOURCE))
            return R.drawable.hopamchuan_logo
        if (song.url.contains(NhacCuaTuiCrawler.SOURCE))
            return R.drawable.nhaccuatui_logo
        if (song.url.contains(NhacVNCrawler.SOURCE))
            return R.drawable.nhacvn_logo
        return R.drawable.ic_loading_gif
    }
}