package com.example.interviewtaskapplication

import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.interviewtaskapplication.BuildConfig.VERSION_CODE
import com.example.interviewtaskapplication.model.Category
import com.example.interviewtaskapplication.model.JsonData
import com.google.android.material.floatingactionbutton.FloatingActionButton


fun ImageView.setColorFilterByWord(colorWord: String) {
    this.setColorFilter(Helper.getColorByWord(colorWord))
}

class MainActivity : AppCompatActivity() {
    private var categoryRecyclerView : RecyclerView? = null
    private var progressBar : ProgressBar? = null
    private var toolbarLinearLayout : LinearLayout? = null
    private var toolbar: Toolbar? = null
    private var s: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        categoryRecyclerView = findViewById(R.id.activity_main__categories_recycler_view)
        progressBar = findViewById(R.id.activity_main__progress_bar)
        toolbarLinearLayout = findViewById(R.id.toolbar_linear_layout)
        toolbar = findViewById(R.id.toolbar)

        categoryRecyclerView?.visibility = View.INVISIBLE
        progressBar?.visibility = View.VISIBLE
        toolbarLinearLayout?.visibility = View.GONE

        toolbar?.title = getString(R.string.activity_main__categories)
        setSupportActionBar(toolbar)

        JsonData.loadData {
            initRecycler()

            categoryRecyclerView?.visibility = View.VISIBLE
            progressBar?.visibility = View.INVISIBLE
        }
    }

    private fun initRecycler() {
        categoryRecyclerView?.layoutManager = LinearLayoutManager(this)
        val categories = JsonData.categories?: ArrayList()
        categoryRecyclerView?.adapter = CategoryAdapter(categories) { category ->
            val intent = ObjectsActivity.newIntent(this@MainActivity, category)
            startActivity(intent)
//            val intent = Intent(this@MainActivity, ObjectsActivity::class.java)
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
//                val options = ActivityOptionsCompat
//                    .makeSceneTransitionAnimation(this, s as View, "robot")
//                startActivity(intent, options.toBundle())
//            } else {
//                startActivity(intent)
//            }
        }
    }
}

class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var categoryNameTextView: TextView? = null
    private var countObjectInCategoryTextView: TextView? = null
    private var circleImageView: ImageView? = null
//    private val

    init {
        categoryNameTextView =
                itemView.findViewById(R.id.categories_recycler_view_item__text_view)
        countObjectInCategoryTextView =
                itemView.findViewById(R.id.categories_recycler_view_item__count_text_view)
        circleImageView = itemView.findViewById(R.id.categories_recycler_view_item__circle_image_view)
    }

    fun bind(category: Category) {
        categoryNameTextView?.text = category.name
        countObjectInCategoryTextView?.text = category.count.toString()
        circleImageView?.setColorFilterByWord(category.color)
    }
}

class CategoryAdapter(private var categories : List<Category>,
                      var onClickListener : (Category) -> Unit)
    : RecyclerView.Adapter<CategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.categories_recycler_view_item, parent, false)
        return CategoryViewHolder(itemView)
    }

    override fun getItemCount() = categories.size

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categories[position]

        holder.bind(category)
        holder.itemView.setOnClickListener {onClickListener(category)}
    }

}