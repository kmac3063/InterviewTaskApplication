package com.example.interviewtaskapplication

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

private const val CATEGORY_TYPE_EXTRA_KEY = "com.example.interviewtaskapplication.CATEGORY_TYPE_EXTRA_KEY"

fun ImageView.setImageFromUrl(url: String) = Picasso.get().load(url).into(this)

class ObjectsActivity : AppCompatActivity() {
    private var objectsRecyclerView: RecyclerView? = null
    private var toolbar: Toolbar? = null
    private var toolbarLinearLayout: LinearLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_objects)

        findAllViewById()
        initView()
    }

    private fun findAllViewById() {
        toolbar = findViewById(R.id.toolbar)
        objectsRecyclerView = findViewById(R.id.activity_objects__objects_recycler_view)
        toolbarLinearLayout = findViewById(R.id.toolbar__linear_layout)
    }

    private fun initView() {
        toolbarLinearLayout?.setOnClickListener { finish() }

        toolbar?.title = getString(R.string.activity_objects__objects)
        setSupportActionBar(toolbar)

        val type: String = intent.getStringExtra(CATEGORY_TYPE_EXTRA_KEY) ?: ""
        initRecycler(type)
    }

    private fun initRecycler(type: String) {
        objectsRecyclerView?.layoutManager = LinearLayoutManager(this)

        val objects = JsonData.getObjectsByCategoryType(type)
        objectsRecyclerView?.adapter = ObjectAdapter(objects) { selectedObject ->
            startActivityWithMapIntent(selectedObject)
        }
    }

    private fun startActivityWithMapIntent(obj: Object) {
        val intent = if (mapAppIsInstalled()) getMapIntent(obj) else getMapAppInstallIntent()
        startActivity(intent)
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun mapAppIsInstalled(): Boolean {
        val uri = Uri.parse("dgis://");
        val intent = Intent(Intent.ACTION_VIEW, uri);

        val activities = packageManager.queryIntentActivities(intent, 0);
        val isIntentSafe = activities.size > 0;

        return isIntentSafe
    }

    private fun getMapAppInstallIntent() : Intent {
        val intent = Intent(Intent.ACTION_VIEW);
        intent.data = Uri.parse("market://details?id=ru.dublgis.dgismobile");
        return intent
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun getMapIntent(obj: Object) : Intent {
        val uri = Uri.parse("dgis://2gis.ru/routeSearch/rsType/car/to/${obj.lon},${obj.lat}")

        val mapIntent = Intent(Intent.ACTION_VIEW, uri)
        mapIntent.resolveActivity(packageManager)?.let {
            return mapIntent
        }
        throw Exception("getAppMapIntentWithoutPath")
    }

    class ObjectViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var objectImageView: ImageView? = null
        private var tittleTextView: TextView? = null
        private var descriptionTextView: TextView? = null

        init {
            objectImageView = itemView.findViewById(
                    R.id.objects_recycler_view_item__image_image_view)
            tittleTextView = itemView.findViewById(
                    R.id.objects_recycler_view_item__tittle_text_view)
            descriptionTextView = itemView.findViewById(
                    R.id.objects_recycler_view_item__description_text_view)
        }

        fun bind(obj: Object) {
            objectImageView?.setImageFromUrl(obj.image)
            tittleTextView?.text = obj.name
            descriptionTextView?.text = obj.description
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
            val obj = objects[position]

            holder.bind(objects[position])
            holder.itemView.setOnClickListener { onClickListener(obj) }
        }

    }

    companion object {
        fun newIntent(context: Context, category: Category) : Intent {
            val intent = Intent(context, ObjectsActivity::class.java)

            intent.putExtra(CATEGORY_TYPE_EXTRA_KEY, category.type)

            return intent
        }
    }
}