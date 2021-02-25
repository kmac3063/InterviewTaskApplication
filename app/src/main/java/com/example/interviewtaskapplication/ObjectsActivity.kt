package com.example.interviewtaskapplication

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.interviewtaskapplication.model.Category
import com.example.interviewtaskapplication.model.JsonData
import java.lang.Exception

private const val EXTRA_KEY = "com.example.interviewtaskapplication.EXTRA_KEY"

class ObjectsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val type: String = savedInstanceState?.get(EXTRA_KEY) as String
        var objects = JsonData.getObjectsByCategoryType(type)

        setContentView(R.layout.activity_objects)


    }

    companion object {
        fun newIntent(context: Context, category: Category) : Intent {
            var intent = Intent(context, ObjectsActivity::class.java).apply {
                putExtra(EXTRA_KEY, category.type)
            }
            return intent
        }
    }
}