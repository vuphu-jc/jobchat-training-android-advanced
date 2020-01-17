package com.example.advancedandroid.utils

import org.jsoup.Jsoup
import org.jsoup.nodes.Document

object JsoupUtils {
    const val USER_AGENT_COMPUTER = "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:5.0) Gecko/20100101 Firefox/5.0"

    fun get(httpURl: String): Document = Jsoup.connect(httpURl)
        .userAgent(USER_AGENT_COMPUTER)
        .get()
}