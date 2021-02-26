package com.example.interviewtaskapplication

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.interviewtaskapplication.model.Category
import com.example.interviewtaskapplication.model.JsonData
import com.example.interviewtaskapplication.model.Object

private const val EXTRA_KEY = "com.example.interviewtaskapplication.EXTRA_KEY"

fun ImageView.setImageFromUrl(url: String) {
    Helper.setImageFromUrl(this, url)
}

class ObjectsActivity : AppCompatActivity() {
    var objectsRecyclerView: RecyclerView? = null

    @SuppressLint("QueryPermissionsNeeded")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_objects)

        Log.d("TAG_1", "onCreate ${ intent.getStringExtra(EXTRA_KEY) ?: ""}")
        val type: String = intent.getStringExtra(EXTRA_KEY) ?: ""
        val objects = JsonData.getObjectsByCategoryType(type)

        objectsRecyclerView = findViewById(R.id.activity_objects__objects_recycler_view)
        objectsRecyclerView?.layoutManager = LinearLayoutManager(this)

        objectsRecyclerView?.adapter = ObjectAdapter(objects) { obj ->
            val intentUri = Uri.parse("geo:${obj.lat},${obj.lon}")

            val mapIntent = Intent(Intent.ACTION_VIEW, intentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            mapIntent.resolveActivity(packageManager)?.let {
                startActivity(mapIntent)
            }

            startActivity(mapIntent)
        }
    }

    class ObjectViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var objectImageView: ImageView? = null
        var tittleTextView: TextView? = null
        var descriptionTextView: TextView? = null

        init {
            objectImageView = itemView.findViewById(
                    R.id.objects_recycler_view_item__image_image_view)
            tittleTextView = itemView.findViewById(
                    R.id.objects_recycler_view_item__tittle_text_view)
            descriptionTextView = itemView.findViewById(
                    R.id.objects_recycler_view_item__description_text_view)
        }

        fun bind(o: Object) {
            objectImageView?.setImageFromUrl(o.image)
            tittleTextView?.text = o.name
            descriptionTextView?.text = o.description
        }
    }

    class ObjectAdapter(var objects: List<Object>,
                        var onClickListener : (Object) -> Unit)
        : RecyclerView.Adapter<ObjectViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ObjectViewHolder {
            val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.objects_recycler_view_item, parent, false)
            return ObjectViewHolder(itemView)
        }

        override fun getItemCount() = objects.size

        override fun onBindViewHolder(holder: ObjectViewHolder, position: Int) {
            val o = objects[position]

            holder.bind(objects[position])
            holder.itemView.setOnClickListener {onClickListener(o)}
        }

    }

    companion object {
        fun newIntent(context: Context, category: Category) : Intent {
            val intent = Intent(context, ObjectsActivity::class.java)

            Log.d("TAG_1", "newIntent ${category.type}")
            intent.putExtra(EXTRA_KEY, category.type)
            return intent
        }
    }
}