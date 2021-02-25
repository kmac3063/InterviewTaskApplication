package com.example.interviewtaskapplication.model

import com.google.gson.Gson
import java.util.*
import kotlin.collections.ArrayList
import kotlin.jvm.javaClass

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

    fun load() {
        //TODO("Not yet implemented")
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
                    image = "",
                    lat = 0.0,
                    lon = 0.0,
                    workingHours = ArrayList())
            )
        }
        return array
    }
}