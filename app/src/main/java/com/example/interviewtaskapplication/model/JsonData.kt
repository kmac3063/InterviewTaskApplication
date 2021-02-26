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

    fun loadData(callback: () -> Unit) {
        if (data != null) {
            callback()
            return
        }

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
    }
}