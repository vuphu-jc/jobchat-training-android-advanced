package com.example.advancedandroid.other.crawler

import android.text.Html
import com.example.advancedandroid.model.Song
import com.example.advancedandroid.model.SongURL
import com.example.advancedandroid.utils.JsoupUtils
import java.lang.Exception

class NhacCuaTuiCrawler {

    companion object {
        const val TAG = "NHAC.CUA.TUI.CRAWLER"
        const val SOURCE = "nhaccuatui.com"
    }

    class ListSong: ListSongCrawler {

        companion object {
            private const val PRELINK = "https://www.nhaccuatui.com/tim-kiem?q="
            private const val SONG_ITEM_SELECT = "li[class=sn_search_single_song]"
            private const val URL_SELECT = "a[href]"
            private const val TITLE_SELECT = "h3[class=title_song]"
            private const val SINGER_SELECT = "h4[class=singer_song]"
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
                    val imageUrl = songItem.select(URL_SELECT).select("img").attr("data-src")
                    val song = Song(name = title, artist = artist, url = url, imageUrl = imageUrl)
                    result.add(song)
                }
                return result
            } catch (e: Exception) {
                return mutableListOf()
            }
        }
    }

    class Lyrics: ContentCrawler<String> {

        companion object {
            private const val DIV_LYRICS_ID = "divLyric"
        }

        override fun getContent(httpURL: String, source: String): String {
            if (httpURL.contains(SOURCE)) {
                try {
                    val docs = JsoupUtils.get(httpURL)
                    val element = docs.getElementById(DIV_LYRICS_ID)
                    return Html.fromHtml(element.html()).toString()
                } catch (e: Exception) {
                    return ""
                }
            }
            return ""
        }
    }
}