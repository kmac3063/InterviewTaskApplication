package com.example.interviewtaskapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.interviewtaskapplication.model.Category
import com.example.interviewtaskapplication.model.JsonData

class MainActivity : AppCompatActivity() {
    private var categoryRecyclerView : RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        categoryRecyclerView = findViewById<RecyclerView>(R.id.activity_main__categories_recycler_view)

        categoryRecyclerView?.layoutManager = LinearLayoutManager(this)
        // TODO Изменить при реализации получения json
        val categories = JsonData.categories?: ArrayList()
        categoryRecyclerView?.adapter = CategoryAdapter(categories) {
                category ->
                val intent = ObjectsActivity.newIntent(this@MainActivity, category)
                startActivity(intent)
        }
    }
}

class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var categoryNameTextView: TextView? = null
    private var countObjectInCategory: TextView? = null
//    private val

    init {
        categoryNameTextView =
                itemView.findViewById(R.id.categories_recycler_view_item__text_view)
        countObjectInCategory =
                itemView.findViewById(R.id.categories_recycler_view_item__count_text_view)
    }

    fun bind(category: Category) {
        categoryNameTextView?.text = category.name
        countObjectInCategory?.text = category.count.toString()
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