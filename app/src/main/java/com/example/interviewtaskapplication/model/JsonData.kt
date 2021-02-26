package com.example.interviewtaskapplication.model

import com.example.interviewtaskapplication.Helper
import okhttp3.internal.immutableListOf
import java.util.*
import kotlin.collections.ArrayList

const val JSON_URL = "https://rsttur.ru/api/base-app/map"

object JsonData {
    var data: Data? = null
    var categories: List<Category>? = null
        get() = data?.categories ?: Collections.emptyList()

    val mapObjectByCategoryType = hashMapOf<String, MutableList<Object>>()

    init {
        val N = 20
        val array = ArrayList<Category>(N)
        for (i in 1..N) {
            array.add(Category(name = "Название категории",
                type = "Тип категории",
                color = "color", count = 100, icon=""))
        }
        data?.categories = array
    }

    fun loadData(callback: () -> Unit) {
        Helper.getJsonStringFromUrl(JSON_URL) { jsonString ->
            val jsonResponseBody = Helper.parseJsonToObject(jsonString, JsonResponseBody::class.java)
            data = jsonResponseBody.data

            sortObjectsToCategories()

            callback()
        }
    }

    fun sortObjectsToCategories() {
        if (data == null || data?.objects == null)
            return

        for (el in data!!.objects) {
            if (mapObjectByCategoryType[el.type] == null) {
                mapObjectByCategoryType[el.type] = ArrayList()
            }
            mapObjectByCategoryType[el.type]?.add(el)
        }
    }


    fun getObjectsByCategoryType(type: String) : List<Object> {
        return mapObjectByCategoryType[type]?.toList()?: Collections.emptyList()

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