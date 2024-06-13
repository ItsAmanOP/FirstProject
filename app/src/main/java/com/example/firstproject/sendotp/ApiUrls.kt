package com.example.firstproject.sendotp

object apiUrls {
    private const val baseUrl = "https://test.msg91.com/api/v5/widget"

    val sendOTP = "$baseUrl/sendOtpMobile"
    val verifyOTP = "$baseUrl/verifyOtp"
    val retryOTP = "$baseUrl/retryOtp"
    val getWidgetProcess = "$baseUrl/getWidgetProcess?widgetId=:widgetId&tokenAuth=:tokenAuth"
}