package com.example.nakshatratak.utility

import android.util.Log
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONObject
import okhttp3.Request

object NotificationUtil {
    fun sendNotification(token: String, title: String, body: String, targetActivity: String) {
        val url = "https://fcm.googleapis.com/v1/projects/nakshatra-tak/messages:send"

        val json = JSONObject().apply {
            put("message", JSONObject().apply {
                put("token", token)
                put("notification", JSONObject().apply {
                    put("title", title)
                    put("body", body)
                })
                put("data", JSONObject().apply {
                    put("targetActivity", targetActivity)
                })
            })
        }

        val requestBody = RequestBody.create("application/json; charset=utf-8".toMediaTypeOrNull(), json.toString())

        val client = OkHttpClient.Builder().build()
        val request = Request.Builder()
            .url(url)
            .post(requestBody)
            .addHeader("Authorization", "Bearer ya29.c.c0ASRK0GZ70Z3gnJnK2P6NoGfodxHLqS6tDvh2hKy0fDJNb4H97hREdtmuRr1kH8XhWeES5iYYY_0AtvQVj24c2QYiGJ6om20ZpdYr-DhkadIlcGWQ-iBYyEVXQyZwt1ozACMoJXZAjz6gZ9Tf9GhoJf-G1fCcM1lipTWG2d-pU-dviwahcZNL18mBKnkeHALmg-Uf7bOiIJ0Qh3BRlXA16quFPAdBNPFqH-Dd07hMKiRBkSYTKTQDpJF86lroe7OesLknaoyvbGn-5PgvaTwoleg2ekvnIlrmaiO7vWHoHfk_eiHBIJyqhozrN1o5VVtYQAsRYbbwTRgL1E9stvNKBerF7wY1ZQm7bSn7hXts_g-48VpRuz9zIGQN384AwOv-RQ9-9MyoJhIXb-cnbIUoUo5tw9sf7ln4BWFVshdbUIoYJv9MdWs2RVIBtrQoJVderxiv_unBVjIJh9yZOlmqvWWtmee6FBjuax-g4XSpvVqulFz-ibmicSn2ubjWrdpnJIZeBJbhV17WveXfweqaa2klW77b_ny_SX5MUhS2QanWMO8z0unXFf3Ro6IQmf1_Iyg-F-BwIqgJsq1208boeb9ZXtsek_SfSj4r7zYyke78aF8gOybtRIpMqeaYcSBtzWqoxo0gX_a_0gRodgWdMOIQt2S_J3XumkzuMYJhzrcFBs3ubVg4-V1m8JpvtIdxwZS9-m1zZS0VBxz29SM5j4WRRaccoYMxm714fzm0paus1YqBV5jt_e8hRk5qbuawdS5cptsBj8m0w1-oWwOqgUSxg0XfW1ygbk8t2ageJXMvzRvFrXWgX_ylrvSnwlB911QVjVs6aqcfh8BzBtpRlB4wV6OmVcQ8SkMwsOqgvI8hf3ZIcSlB1xU-g9zrZ_y5tRZBa6x1ldjyb_RneRUZlfqBicnknuf08_f7lfse3Sl5n_szuJijcVya4ShVyYXSq3kj0Qxe4qUqXZ1pVpJFwh6t5oF191p6i7iQ--0Yc37meegFpzrR_4Q")
            .build()

        client.newCall(request).enqueue(object : okhttp3.Callback {
            override fun onFailure(call: okhttp3.Call, e: java.io.IOException) {
                Log.e("NotificationUtil", "Failed to send notification: ${e.message}")
            }

            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                if (response.isSuccessful) {
                    Log.d("NotificationUtil", "Notification sent successfully: ${response.body?.string()}")
                } else {
                    Log.e("NotificationUtil", "Failed to send notification: ${response.message}")
                }
            }
        })
    }
}