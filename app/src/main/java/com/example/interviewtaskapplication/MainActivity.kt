package com.example.interviewtaskapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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
        categoryRecyclerView?.adapter = CategoryAdapter(fillFakeCategories())
    }

    private fun fillFakeCategories() : List<Category> {
        val N = 10
        val array = ArrayList<Category>(N)
        for (i in 1..N) {
            array.add(Category(name = "Название категории",
            type = "Тип категории",
            color = "color", count = 100, icon=""))
        }
        return array
    }
}

class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var categoryNameTextView: TextView? = null
    private var countObjectInCategory: TextView? = null

    init {
        categoryNameTextView =
                itemView.findViewById(R.id.categories_recycler_view_item__text_view)
        countObjectInCategory =
                itemView.findViewById(R.id.categories_recycler_view_item__count_text_view)
    }

    fun bind(holder: CategoryViewHolder, category: Category) {
        holder.categoryNameTextView?.text = category.name
        holder.countObjectInCategory?.text = category.count.toString()
    }
}

class CategoryAdapter(private var categories : List<Category>) : RecyclerView.Adapter<CategoryViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.categories_recycler_view_item, parent, false)
        return CategoryViewHolder(itemView)
    }

    override fun getItemCount() = categories.size

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(holder, categories[position])
    }

}