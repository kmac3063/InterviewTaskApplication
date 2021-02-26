package com.example.interviewtaskapplication.model

import com.example.interviewtaskapplication.Helper
import com.google.gson.Gson
import java.util.*
import kotlin.collections.ArrayList
import kotlin.jvm.javaClass

const val JSON_URL = "https://rsttur.ru/api/base-app/map"

object JsonData {
    var categories: List<Category>? = null
    init {
        val N = 20
        val array = ArrayList<Category>(N)
        for (i in 1..N) {
            array.add(Category(name = "Название категории",
                type = "Тип категории",
                color = "color", count = 100, icon=""))
        }
        categories = array
    }

    fun loadData(callback: () -> Unit) {
        Helper.getJsonStringFromUrl(JSON_URL) { jsonString ->
//            Helper.parseJsonToObject()

            callback()
        }

//        1) скачать json
//        2) распарсить json
//        3) собрать метод для получения объектов по типу категории
    }

    fun getObjectsByCategoryType(type: String) : List<Object> {
        val N = 20
        val array = ArrayList<Object>(N)
        for (i in 1..N) {
            array.add(
                Object(name = "Название категории",
                    type = "Тип категории",
                    color = "color",
                    id = 100,
                    icon="",
                    description = "Описание описание описание",
                    image = "https://www.google.com/images/branding/googlelogo/2x/googlelogo_color_92x30dp.png",
                    lat = 0.0,
                    lon = 0.0,
                    workingHours = ArrayList())
            )
        }
        return array
    }
}