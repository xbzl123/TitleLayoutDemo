package com.module.titlelayoutdemo.deepseek

import android.util.Log
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import org.json.JSONObject
import java.io.IOException
import java.util.concurrent.TimeUnit

class DeepSeekApiClient (callback:DeepSeekResult){
    private val client = OkHttpClient().newBuilder()
        .connectTimeout(60,TimeUnit.SECONDS)
        .readTimeout(60,TimeUnit.SECONDS).build()
    private val apiKey = "sk-e7a882d208464257a28b84bdb03f0e" // 替换为你的API密钥
    private val apiUrl = "https://api.deepseek.com/v1/chat/completions"

    private val resultCallback = callback

    fun sendRequest(prompt: String) {
        val mediaType = "application/json".toMediaTypeOrNull()
        val requestBody = RequestBody.create(
            mediaType,
            """
            {
                "model": "deepseek-chat",
                "messages": [
                    {"role": "user", "content": "$prompt"}
                ]
            }
            """.trimIndent()
        )

        val request = Request.Builder()
            .url(apiUrl)
            .post(requestBody)
            .addHeader("Content-Type", "application/json")
            .addHeader("Authorization", "Bearer $apiKey")
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                // 处理请求失败
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                if (!response.isSuccessful) {
                    // 处理响应失败
                    throw IOException("Unexpected code ${response.code}")
                }

                // 处理响应成功
                val responseBody = response.body?.string()
                Log.e("onResponse","responseBody:$responseBody")
                if (responseBody != null) {
                    val jsonObject = JSONObject(responseBody)
                    val choices  = jsonObject.getJSONArray("choices")[0] as JSONObject
                    val message = choices.getJSONObject("message")
                    val content  = message.get("content")
                    resultCallback.onResult(content.toString())
                }
                println(responseBody)
            }
        })
    }
}

interface DeepSeekResult{
    fun onResult(resut:String)
}
