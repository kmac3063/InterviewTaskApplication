package com.example.interviewtaskapplication

import android.graphics.Color
import android.os.Handler
import android.os.Looper
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.*
import java.util.concurrent.Callable
import java.util.concurrent.Executors


object Helper {
    fun getJsonStringFromUrl(url: String, resultCallback: (String) -> Unit) {
        TaskRunner().executeAsync(LoadJsonTask(url)) { jsonString ->
            resultCallback(jsonString)
        }
    }

    class TaskRunner {
        private val executor = Executors.newSingleThreadExecutor()
        private val handler = Handler(Looper.getMainLooper())

        fun <R>executeAsync(callable: Callable<R>, callback: (R) -> Unit) {
            executor.execute {
                val result = callable.call();
                handler.post {
                    callback(result)
                }
            };
        }
    }

    class LoadJsonTask(private val url: String) : Callable<String> {
        override fun call(): String {
            val request = Request.Builder().url(url).get()

            val client = OkHttpClient.Builder().build()
            val response = client.newCall(request.build()).execute()
            return response.body?.string()?: ""
        }

    }

    fun <T>parseJsonToObject(jsonString: String, classObject: Class<T>): T
            = Gson().fromJson<T>(jsonString, classObject)

    fun getColorByWord(colorWord: String) = when (colorWord.toLowerCase(Locale.ROOT)) {
        "primary" -> Color.parseColor("#0275d8")
        "secondary" -> Color.parseColor("#9e9ea0")
        "success" -> Color.parseColor("#5cb85c")
        "danger" -> Color.parseColor("#d9534f")
        "warning" -> Color.parseColor("#f0ad4e")
        "info" -> Color.parseColor("#5bc0de")
        "light" -> Color.parseColor("#fefefe")
        "dark" -> Color.parseColor("#d6d8d9")
        else -> Color.parseColor("#48b3ff")
    }
}