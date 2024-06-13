package com.msg91com.sendotp

object ApiUrls {
    private const val baseUrl = "https://test.msg91.com/api/v5/widget"

    const val sendOTP = "$baseUrl/sendOtpMobile"
    const val verifyOTP = "$baseUrl/verifyOtp"
    const val retryOTP = "$baseUrl/retryOtp"
    const val getWidgetProcess = "$baseUrl/getWidgetProcess?widgetId=:widgetId&tokenAuth=:tokenAuth"
}