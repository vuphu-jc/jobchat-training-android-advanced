package com.example.advancedandroid.other.crawler

import android.text.Html
import com.example.advancedandroid.model.Song
import com.example.advancedandroid.model.SongURL
import com.example.advancedandroid.utils.JsoupUtils
import java.lang.Exception

class HopAmChuanCrawler {

    companion object {
        const val TAG = "HOP.AM.CHUAN.CRAWLER"
        const val SOURCE = "hopamchuan.com"
    }

    class ListSong(): ListSongCrawler {
        companion object {
            private const val PRELINK = "https://hopamchuan.com/search?q="
            private const val SONG_ITEM_SELECT = "div[class=song-item]"
            private const val URL_SELECT = "a[href]"
            private const val TITLE_SELECT = "song-title-singers"
            private const val SINGER_SELECT = "song-singers"
        }

        override fun search(keyword: String, limit: Int): List<Song> {
            try {
                val result = mutableListOf<Song>()
                val docs = JsoupUtils.get(PRELINK + keyword)
                val songItems = docs.select(SONG_ITEM_SELECT)
                for ((counter, songItem) in songItems.withIndex()) {
                    if (counter == limit)
                        break

                    val title = songItem.select(TITLE_SELECT).text()
                    val artist = songItem.select(SINGER_SELECT).text()
                    val url = songItem.select(URL_SELECT).attr("href")
                    val song = Song(name = title, artist = artist, url = url)
                    result.add(song)
                }
                return result
            } catch (e: Exception) {
                return mutableListOf()
            }
        }
    }

    class Chords: ContentCrawler<String> {

        companion object {
            private const val SONG_LYRICS_ID = "song-lyric"
        }

        override fun getContent(httpURL: String, source: String): String {
            if (httpURL.contains(SOURCE)) {
                try {
                    val docs = JsoupUtils.get(httpURL)
                    val element = docs.getElementById(SONG_LYRICS_ID)
                    return Html.fromHtml(element.html()).replace("\n\n".toRegex(),"\n")
                } catch (e: Exception) {
                    return ""
                }
            }
            return ""
        }
    }
}