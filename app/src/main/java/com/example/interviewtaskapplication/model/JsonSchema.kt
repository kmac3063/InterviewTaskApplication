package com.example.interviewtaskapplication.model

class Example(var success: Boolean, var error: Any, var time: String, var data: Data)

class Data(var geo: Geo, var categories: List<Category>, var objects: List<Object>)

class Geo(var lat: Double, var lon: Double)

class Category(var name: String,
               var type: String,
               var icon: String,
               var color: String,
               var count: Int)

class Object(var id: Int,
             var description: String,
             var name: String,
             var image: String,
             var type: String,
             var icon: String,
             var color: String,
             var lat: Double,
             var lon: Double,
             var workingHours: List<WorkingHour>)


class WorkingHour(var days: List<Int>, var from: String, var to: String)