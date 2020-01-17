package com.example.advancedandroid.other.crawler

import android.text.Html
import com.example.advancedandroid.model.Song
import com.example.advancedandroid.model.SongURL
import com.example.advancedandroid.utils.HttpUrlConnectionUtils
import com.example.advancedandroid.utils.JsoupUtils
import com.google.gson.Gson
import org.json.JSONObject
import java.lang.Exception

class NhacVNCrawler {

    companion object {
        const val TAG = "NHAC.VN.CRAWLER"
        const val SOURCE = "nhac.vn"
    }

    class ListSong: ListSongCrawler {


        companion object {
            private const val PRELINK = "https://nhac.vn/search?q="
            private const val SONG_ITEM_SELECT = "li[class=song-list-new-item]"
            private const val URL_SELECT = "a[href]"
            private const val TITLE_SELECT = "div[class=info]"
            private const val ARTIST_SELECT = "div[class=info]"
        }

        override fun search(keyword: String, limit: Int): List<Song> {
            try {
                val result = mutableListOf<Song>()
                val docs = JsoupUtils.get(PRELINK + keyword)
                val songItems = docs.select(SONG_ITEM_SELECT)
                for ((counter, songItem) in songItems.withIndex()) {
                    if (counter == limit)
                        break

                    val title = songItem.select(TITLE_SELECT).select("h3").text()
                    val artist = songItem.select(ARTIST_SELECT).select("h4").text()
                    val url = songItem.select(URL_SELECT).attr("href")
                    val imageUrl = songItem.select(URL_SELECT).select("img").attr("src")
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

    class Mp3URL: ContentCrawler<String> {
        companion object {
            private const val MATCH = "\\{\"file\":\""
        }

        override fun getContent(httpURL: String, source: String): String {
            if (httpURL.contains(SOURCE)) {
                try {
                    val content = HttpUrlConnectionUtils.getContent(httpURL)
                    val allPos = MATCH.toRegex().findAll(httpURL)
                    for (pos in allPos) {

                    }
                } catch (e: Exception) {
                    return ""
                }
            }
            return ""
        }
    }

    class VttURL: ContentCrawler<String> {
        override fun getContent(httpURL: String, source: String): String {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

    }
}