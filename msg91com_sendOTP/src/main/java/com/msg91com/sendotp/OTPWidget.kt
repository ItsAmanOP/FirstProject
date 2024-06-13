package com.msg91com.sendotp

import org.json.JSONObject

object OTPWidget {

    fun sendOTP(widgetId: String, tokenAuth: String, identifier: String): String {

        val payload = JSONObject().apply {
            put("widgetId", widgetId)
            put("tokenAuth", tokenAuth)
            put("identifier", identifier)
        }

        return NetworkUtils.post(ApiUrls.sendOTP, payload.toString())
    }

    fun verifyOTP(widgetId: String, tokenAuth: String, otp: String, reqId: String): String {

        val payload = JSONObject().apply {
            put("widgetId", widgetId)
            put("tokenAuth", tokenAuth)
            put("otp", otp)
            put("reqId", reqId)
        }

        return NetworkUtils.post(ApiUrls.verifyOTP, payload.toString())
    }

    fun retryOTP(widgetId: String, tokenAuth: String, retryChannel: Number, reqId: String): String {

        val payload = JSONObject().apply {
            put("widgetId", widgetId)
            put("tokenAuth", tokenAuth)
            put("retryChannel", retryChannel)
            put("reqId", reqId)
        }

        return NetworkUtils.post(ApiUrls.retryOTP, payload.toString())
    }

    fun getWidgetProcess(widgetId: String, tokenAuth: String): String {

        val url = ApiUrls.getWidgetProcess
            .replace(":widgetId", widgetId)
            .replace(":tokenAuth", tokenAuth)

        return NetworkUtils.get(url)
    }
}