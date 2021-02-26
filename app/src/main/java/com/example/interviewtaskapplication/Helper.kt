package com.example.interviewtaskapplication

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.ImageView
import com.example.interviewtaskapplication.model.JsonResponseBody
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import java.net.URL
import java.util.*
import java.util.concurrent.Callable
import java.util.concurrent.Executors


object Helper {
//    fun setImageFromUrl(imageView: ImageView, url: String) {
//        Picasso.
//        TaskRunner().executeAsync(DownloadImageTask(url)) { bitmap ->
//            imageView.setImageBitmap(bitmap)
//        }
//    }

    fun getJsonStringFromUrl(url: String, callback: (String) -> Unit) {
        TaskRunner().executeAsync(LoadJsonTask(url)) { json ->
            callback(json)
        }
    }

    class TaskRunner {
        val executor = Executors.newSingleThreadExecutor()
        val handler = Handler(Looper.getMainLooper())

        fun <R>executeAsync(callable: Callable<R>, callback: (R) -> Unit) {
            executor.execute {
                val result = callable.call();
                handler.post {
                    callback(result)
                }
            };
        }
    }

//    class DownloadImageTask(val url: String) : Callable<Bitmap> {
//        override fun call(): Bitmap {
//            val inputStream = URL(url).openStream()
//            val bitmap = BitmapFactory.decodeStream(inputStream)
//
//            return bitmap
//        }
//    }

    class LoadJsonTask(val url: String) : Callable<String> {
        override fun call(): String {
            val client: OkHttpClient = OkHttpClient.Builder().build();

            val request = Request.Builder()
                    .url(url)
                    .get()

            val response = client.newCall(request.build()).execute();
//            Log.d("TAG_1", "${response.body?.string()}")
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