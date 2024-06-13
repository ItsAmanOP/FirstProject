package com.example.firstproject

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.firstproject.ui.theme.FirstProjectTheme

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import com.msg91com.sendotp.OTPWidget
//import com.example.firstproject.sendotp.OTPWidget
//import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import kotlinx.coroutines.*


class MainActivity : ComponentActivity() {
    private lateinit var identifierEditText: EditText
    private lateinit var otpEditText: EditText
    private lateinit var sendOTPButton: Button
    private lateinit var verifyOTPButton: Button
    private lateinit var retryOTPButton: Button
    private lateinit var getWidgetProcessButton: Button
    private lateinit var resultTextView: TextView
    private lateinit var loadingProgressBar: ProgressBar

    var reqId = "";

    val widgetId = "326b6b6c7330313734343537"
    val tokenAuth = "278060TX9AIhxEpR7H6427de5cP1"

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        identifierEditText = findViewById(R.id.identifierEditText)
        otpEditText = findViewById(R.id.otpEditText)
        sendOTPButton = findViewById(R.id.sendOTPButton)
        verifyOTPButton = findViewById(R.id.verifyOTPButton)
        retryOTPButton = findViewById(R.id.retryOTPButton)
        getWidgetProcessButton = findViewById(R.id.getWidgetProcessButton)
        resultTextView = findViewById(R.id.resultTextView)
        loadingProgressBar = findViewById(R.id.loadingProgressBar)

        sendOTPButton.setOnClickListener { sendOTP() }
        verifyOTPButton.setOnClickListener { verifyOTP() }
        retryOTPButton.setOnClickListener { retryOTP() }
        getWidgetProcessButton.setOnClickListener { getWidgetProcess() }
    }

//    private fun sendOTP() {
//        val identifier = identifierEditText.text.toString()
//        CoroutineScope(Dispatchers.Main).launch {
//            val body = JSONObject().apply {
//                put("identifier", identifier)
//                put("widgetId", widgetId)
//                put("tokenAuth", tokenAuth)
//            }
//            val result = OTPWidget.sendOTP(body)
//            resultTextView.text = result
//        }
//    }

    private fun sendOTP() {
        showLoading()
        val identifier = identifierEditText.text.toString()
        coroutineScope.launch {
            try {
                val body = JSONObject().apply {
                    put("identifier", identifier)
                    put("widgetId", widgetId)
                    put("tokenAuth", tokenAuth)
                }
                val result = withContext(Dispatchers.IO) {
                    OTPWidget.sendOTP(widgetId, tokenAuth, identifier)
                }
                val jsonObject = JSONObject(result)
                val message = jsonObject.getString("message")
                reqId = message;

                println("SendOTP Success Message: $message")
                resultTextView.text = result

            } catch (e: Exception) {
                println(e)
                resultTextView.text = "Error: ${e.message}"
            }
            finally {
                hideLoading()
            }
        }
    }

    private fun verifyOTP() {
        showLoading()
        val otp = otpEditText.text.toString()
        coroutineScope.launch {
            try {
                val result = withContext(Dispatchers.IO) {
                    OTPWidget.verifyOTP(widgetId, tokenAuth, otp, reqId)
                }
                val jsonObject = JSONObject(result)
                val responseType = jsonObject.getString("type")
                val message = jsonObject.getString("message")

                if (responseType != "error") {
                    reqId = message;
                }

                println("VerifyOTP Success Message: $message")
                resultTextView.text = result
            } catch (e: Exception) {
                resultTextView.text = "Error: ${e.message}"
            } finally {
                hideLoading()
            }
        }
    }

    private fun retryOTP() {
        showLoading()
        coroutineScope.launch {
            try {
                val result = withContext(Dispatchers.IO) {
                    OTPWidget.retryOTP(widgetId, tokenAuth, 4, reqId)
                }
                resultTextView.text = result
            } catch (e: Exception) {
                resultTextView.text = "Error: ${e.message}"
            } finally {
                hideLoading()
            }
        }
    }

    private fun getWidgetProcess() {
        showLoading()
        coroutineScope.launch {
            try {
                val result = withContext(Dispatchers.IO) {
                    OTPWidget.getWidgetProcess(widgetId, tokenAuth)
                }
                resultTextView.text = result
            } catch (e: Exception) {
                resultTextView.text = "Error: ${e.message}"
            } finally {
                hideLoading()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
    }

    private fun showLoading() {
        loadingProgressBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        loadingProgressBar.visibility = View.GONE
    }

//    private fun verifyOTP() {
//        val otp = otpEditText.text.toString() // Assuming the OTP is entered in the same field
////        val requestId = "123" // Provide the actual request ID here
//        CoroutineScope(Dispatchers.Main).launch {
//            val body = JSONObject().apply {
//                put("otp", otp)
//                put("widgetId", widgetId)
//                put("tokenAuth", tokenAuth)
//            }
//
//            println(body);
//            try {
//                val result = OTPWidget.verifyOTP(body)
//                resultTextView.text = result
//            }
//            catch (error: Exception) {
//                val e =
//                println("Error sending OTP: ${error.message}")
//            }
//        }
//    }
//
//    private fun retryOTP() {
//        val requestId = "123" // Provide the actual request ID here
//        val channel = "email" // Provide the channel for retrying the OTP
//        CoroutineScope(Dispatchers.Main).launch {
//            val body = JSONObject().apply {
//                put("reqId", requestId)
//                put("channel", channel)
//            }
//            val result = OTPWidget.retryOTP(body)
//            resultTextView.text = result
//        }
//    }
//
//    private fun getWidgetProcess() {
//            println("-----------URL------------");
//            var str = "Fault";
//        str += "Hello";
//        CoroutineScope(Dispatchers.Main).launch {
//            val result = OTPWidget.getWidgetProcess(widgetId, tokenAuth)
//            resultTextView.text = result
//        }
//    }
}