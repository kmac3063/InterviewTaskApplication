package com.example.interviewtaskapplication.model

import com.example.interviewtaskapplication.Helper
import java.util.*
import kotlin.collections.ArrayList

const val JSON_DATA_URL = "https://rsttur.ru/api/base-app/map"

object JsonData {
    var categories: List<Category> = Collections.emptyList()
        get() = data?.categories ?: Collections.emptyList()
    private var data: Data? = null
    private val mapObjectByCategoryType = hashMapOf<String, MutableList<Object>>()

    fun loadData(loadedCallback: () -> Unit) {
        if (data != null) {
            loadedCallback()
            return
        }

        Helper.getJsonStringFromUrl(JSON_DATA_URL) { jsonString ->
            val jsonResponseBody = Helper.parseJsonToObject(jsonString, JsonResponseBody::class.java)
            data = jsonResponseBody.data

            sortObjectsToCategories()

            loadedCallback()
        }
    }

    private fun sortObjectsToCategories() {
        if (data == null || data?.objects == null)
            return

        for (obj in data!!.objects) {
            if (mapObjectByCategoryType[obj.type] == null) {
                mapObjectByCategoryType[obj.type] = ArrayList()
            }
            mapObjectByCategoryType[obj.type]?.add(obj)
        }
    }

    fun getObjectsByCategoryType(type: String) : List<Object> {
        return mapObjectByCategoryType[type]?.toList()?: Collections.emptyList()
    }
}