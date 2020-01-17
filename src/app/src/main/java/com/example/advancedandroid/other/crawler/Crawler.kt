package com.example.advancedandroid.other.crawler

import com.example.advancedandroid.model.Song
import com.example.advancedandroid.model.SongURL

interface SearchCrawler<T> {
    fun search(keyword: String, limit: Int = 3): T
}

interface ListSongCrawler: SearchCrawler<List<Song>> {

}

interface ContentCrawler<T> {
    fun getContent(httpURL: String, source: String = ""): T
}