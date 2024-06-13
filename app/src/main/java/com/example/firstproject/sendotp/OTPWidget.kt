package com.example.firstproject.sendotp


import org.json.JSONObject

object OTPWidget {

    private var widgetId: String = ""
    private var tokenAuth: String = ""

    private fun checkInitialization(): Boolean {
        return if (widgetId.isEmpty() || tokenAuth.isEmpty()) {
            println("Widget not initialized. Call initializeWidget before using any method.")
            false
        } else {
            true
        }
    }

    suspend fun initializeWidget(widgetId: String, tokenAuth: String) {
        OTPWidget.widgetId = widgetId
        OTPWidget.tokenAuth = tokenAuth
    }

//    suspend fun sendOTP(body: JSONObject): String? {
////        if (!checkInitialization()) return null
//
////        val payload = body.apply {
////            put("widgetId", widgetId)
////            put("tokenAuth", tokenAuth)
////        }
//
//        return try {
//            ApiService.sendOTP(body)
//        } catch (e: Exception) {
//            println("Error sending OTP: ${e.message}")
//            throw e
//        }
//    }

    suspend fun sendOTP(widgetId: String, tokenAuth: String, identifier: String): String? {

        val payload = JSONObject().apply {
            put("widgetId", widgetId)
            put("tokenAuth", tokenAuth)
            put("identifier", identifier)
        }

        return try {
            ApiService.sendOTP(payload)
        } catch (e: Exception) {
            println("Error sending OTP: ${e.message}")
            throw e
        }
    }

    suspend fun verifyOTP(widgetId: String, tokenAuth: String, otp: String, reqId: String): String? {

        val payload = JSONObject().apply {
            put("widgetId", widgetId)
            put("tokenAuth", tokenAuth)
            put("otp", otp)
            put("reqId", reqId)
        }

        return try {
            ApiService.verifyOTP(payload)
        } catch (e: Exception) {
            println("Error verifying OTP: ${e.message}")
            throw e
        }
    }

    suspend fun retryOTP(widgetId: String, tokenAuth: String, retryChannel: Number, reqId: String): String? {

        val payload = JSONObject().apply {
            put("widgetId", widgetId)
            put("tokenAuth", tokenAuth)
            put("retryChannel", retryChannel)
            put("reqId", reqId)
        }

        return try {
            ApiService.retryOTP(payload)
        } catch (e: Exception) {
            println("Error retrying OTP: ${e.message}")
            throw e
        }
    }

    suspend fun getWidgetProcess(widgetId: String, tokenAuth: String): String? {

        return try {
            ApiService.getWidgetProcess(widgetId, tokenAuth)
        } catch (e: Exception) {
            println("Error getting widget process: ${e.message}")
            throw e
        }
    }

//    suspend fun verifyOTP(body: JSONObject): String? {
////        if (!checkInitialization()) return null
//
////        val payload = body.apply {
////            put("widgetId", widgetId)
////            put("tokenAuth", tokenAuth)
////        }
//
//        return try {
//            ApiService.verifyOTP(body)
//        } catch (e: Exception) {
//            println("Error verifying OTP: ${e.message}")
//            throw e
//        }
//    }

//    suspend fun retryOTP(body: JSONObject): String? {
////        if (!checkInitialization()) return null
////
////        val payload = body.apply {
////            put("widgetId", widgetId)
////            put("tokenAuth", tokenAuth)
////        }
//
//        return try {
//            ApiService.retryOTP(body)
//        } catch (e: Exception) {
//            println("Error retrying OTP: ${e.message}")
//            throw e
//        }
//    }

}