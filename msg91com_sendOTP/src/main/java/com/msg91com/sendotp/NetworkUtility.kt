package com.msg91com.sendotp

import java.io.*
import java.net.HttpURLConnection
import java.net.URL

object NetworkUtils {
    @Throws(IOException::class)
    fun post(urlString: String, jsonBody: String): String {
        val url = URL(urlString)
        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "POST"
        connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8")
        connection.doOutput = true
        connection.doInput = true

        val writer = BufferedWriter(OutputStreamWriter(connection.outputStream, "UTF-8"))
        writer.write(jsonBody)
        writer.flush()
        writer.close()

        val responseCode = connection.responseCode
        val redirectUrl = connection.getHeaderField("Location")

        return if (responseCode in 300..399 && redirectUrl != null) {
            post(redirectUrl, jsonBody)
        } else {
            val inputStream = if (responseCode >= 200 && responseCode < 400) {
                connection.inputStream
            } else {
                connection.errorStream
            }
            inputStream.bufferedReader().use { it.readText() }
        }
    }

    @Throws(IOException::class)
    fun get(urlString: String): String {
        val url = URL(urlString)
        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "GET"
        connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8")
        connection.doInput = true

        val responseCode = connection.responseCode
        val redirectUrl = connection.getHeaderField("Location")

        return if (responseCode in 300..399 && redirectUrl != null) {
            get(redirectUrl)
        } else {
            val inputStream = if (responseCode >= 200 && responseCode < 400) {
                connection.inputStream
            } else {
                connection.errorStream
            }
            inputStream.bufferedReader().use { it.readText() }
        }
    }
}