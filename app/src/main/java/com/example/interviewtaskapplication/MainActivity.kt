package com.example.interviewtaskapplication

import android.content.Intent
import android.graphics.Color
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.interviewtaskapplication.model.Category
import com.example.interviewtaskapplication.model.JsonData


fun ImageView.setColorFilterByWord(colorWord: String) {
    this.setColorFilter(Helper.getColorByWord(colorWord))
}

class MainActivity : AppCompatActivity() {
    private var categoryRecyclerView : RecyclerView? = null
    private var progressBar : ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        categoryRecyclerView = findViewById(R.id.activity_main__categories_recycler_view)
        progressBar = findViewById(R.id.activity_main__progress_bar)

        categoryRecyclerView?.visibility = View.INVISIBLE
        progressBar?.visibility = View.VISIBLE

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