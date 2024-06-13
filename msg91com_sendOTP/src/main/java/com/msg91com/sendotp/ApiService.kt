package com.msg91com.sendotp

import org.json.JSONObject

object ApiService {

    fun sendOTP(body: JSONObject): String {
        return NetworkUtils.post(ApiUrls.sendOTP, body.toString())
    }

    fun verifyOTP(body: JSONObject): String {
        return NetworkUtils.post(ApiUrls.verifyOTP, body.toString())
    }

    fun retryOTP(body: JSONObject): String {
        return NetworkUtils.post(ApiUrls.retryOTP, body.toString())
    }

    fun getWidgetProcess(widgetId: String, tokenAuth: String): String {
        val url = ApiUrls.getWidgetProcess
            .replace(":widgetId", widgetId)
            .replace(":tokenAuth", tokenAuth)
        println(url);
        return NetworkUtils.get(url)
    }
}