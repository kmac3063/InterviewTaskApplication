package com.example.interviewtaskapplication

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.location.LocationManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.interviewtaskapplication.model.Category
import com.example.interviewtaskapplication.model.JsonData
import com.example.interviewtaskapplication.model.Object
import com.squareup.picasso.Picasso
import java.lang.Exception

private const val EXTRA_KEY = "com.example.interviewtaskapplication.EXTRA_KEY"

fun ImageView.setImageFromUrl(url: String) = Picasso.get().load(url).into(this)

class ObjectsActivity : AppCompatActivity() {
    var objectsRecyclerView: RecyclerView? = null
    var toolbar: Toolbar? = null
    var toolbarLinearLayout: LinearLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_objects)

        toolbar = findViewById(R.id.toolbar)
        objectsRecyclerView = findViewById(R.id.activity_objects__objects_recycler_view)
        toolbarLinearLayout = findViewById(R.id.toolbar_linear_layout)

        initView()
    }

    override fun onDestroy() {
        super.onDestroy()
        toolbar = null
        toolbarLinearLayout = null
    }

    fun initView() {
        toolbarLinearLayout?.setOnClickListener { finish() }

        toolbar?.title = getString(R.string.activity_objects__objects)
        setSupportActionBar(toolbar)
//        supportActionBar?.setDisplayShowTitleEnabled(false)
        val type: String = intent.getStringExtra(EXTRA_KEY) ?: ""
        initRecycler(type)
    }

    private fun initRecycler(type: String) {
        objectsRecyclerView?.layoutManager = LinearLayoutManager(this)

        val objects = JsonData.getObjectsByCategoryType(type)
        objectsRecyclerView?.adapter = ObjectAdapter(objects) { obj ->
            startActivityWithMapIntent(obj)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.objects_activity_menu, menu)
        return true
    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            R.id.objects_activity_menu__back_item -> finish()
//        }
//        return true
//    }

    private fun startActivityWithMapIntent(obj: Object) {
        val intent = if (appInstalled()) getMapIntent(obj) else getAppInstallIntent()
        startActivity(intent)
    }

    private fun appInstalled(): Boolean {
        val uri = Uri.parse("dgis://");
        val intent = Intent(Intent.ACTION_VIEW, uri);

        val activities = packageManager.queryIntentActivities(intent, 0);
        val isIntentSafe = activities.size > 0;

        return isIntentSafe
    }

    private fun getAppInstallIntent() : Intent {
        val intent = Intent(Intent.ACTION_VIEW);
        intent.data = Uri.parse("market://details?id=ru.dublgis.dgismobile");
        return intent
    }

    private fun getMapIntent(obj: Object) : Intent {
        val uri = Uri.parse("dgis://2gis.ru/routeSearch/rsType/car/to/${obj.lon},${obj.lat}")

        val mapIntent = Intent(Intent.ACTION_VIEW, uri)
        mapIntent.resolveActivity(packageManager)?.let {
            return mapIntent
        }
        throw Exception("getAppMapIntentWithoutPath")
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