package com.example.advancedandroid.utils

import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


object HttpUrlConnectionUtils {
    fun getContent(url: String): String {
        val url = URL(url)
        val httpConn: HttpURLConnection = url.openConnection() as HttpURLConnection
        httpConn.apply {
            allowUserInteraction = false
            instanceFollowRedirects = true
            requestMethod = "GET"
            connect()
        }

        if (httpConn.responseCode == HttpURLConnection.HTTP_OK) {
            var bufferReader: BufferedReader? = null
            bufferReader = BufferedReader(InputStreamReader(httpConn.inputStream))
            val sb = StringBuilder()
            var s: String? = null
            while (bufferReader.readLine().also { s = it } != null) {
                sb.append(s)
                sb.append("\n")
            }
            return sb.toString()
        }

        return ""
    }
}