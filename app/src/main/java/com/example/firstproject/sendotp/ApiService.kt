package com.example.firstproject.sendotp

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

object ApiService {

    private suspend fun postAPI(url: String, body: JSONObject): String {
        return withContext(Dispatchers.IO) {
            val urlConnection = (URL(url).openConnection() as HttpURLConnection).apply {
                requestMethod = "POST"
                setRequestProperty("Content-Type", "application/json; utf-8")
                doOutput = true
                outputStream.write(body.toString().toByteArray())
            }
            try {
                val inputStream = urlConnection.inputStream
                val reader = BufferedReader(InputStreamReader(inputStream))
                val response = StringBuilder()
                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    response.append(line)
                }
                response.toString()
            } finally {
                urlConnection.disconnect()
            }
        }
    }

    private suspend fun getAPI(url: String): String {
        return withContext(Dispatchers.IO) {
            val urlConnection = URL(url).openConnection() as HttpURLConnection
            try {
                val inputStream = urlConnection.inputStream
                val reader = BufferedReader(InputStreamReader(inputStream))
                val response = StringBuilder()
                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    response.append(line)
                }
                response.toString()
            } finally {
                urlConnection.disconnect()
            }
        }
    }

    suspend fun sendOTP(body: JSONObject): String {
        return NetworkUtils.post(apiUrls.sendOTP, body.toString())
//        return postAPI(apiUrls.sendOTP, body)
    }

    suspend fun verifyOTP(body: JSONObject): String {
        return NetworkUtils.post(apiUrls.verifyOTP, body.toString())
//        return postAPI(apiUrls.verifyOTP, body)
    }

    suspend fun retryOTP(body: JSONObject): String {
        return NetworkUtils.post(apiUrls.retryOTP, body.toString())
//        return postAPI(apiUrls.retryOTP, body)
    }

    suspend fun getWidgetProcess(widgetId: String, tokenAuth: String): String {
        val url = apiUrls.getWidgetProcess
            .replace(":widgetId", widgetId)
            .replace(":tokenAuth", tokenAuth)
        println(url);
        return NetworkUtils.get(url)
//        return getAPI(url)
    }
}