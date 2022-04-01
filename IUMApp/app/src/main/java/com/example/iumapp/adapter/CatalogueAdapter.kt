package com.example.iumapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.iumapp.R
import com.example.iumapp.database.lesson.Lesson

class CatalogueAdapter(
    private val dataset: List<Lesson>
): RecyclerView.Adapter<CatalogueAdapter.CatalogueViewHolder>()  {

    class CatalogueViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.item_title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatalogueViewHolder {
        return CatalogueViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.lesson_list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CatalogueViewHolder, position: Int) {
        val item = dataset[position]
        holder.textView.text = item.name
    }

    override fun getItemCount() = dataset.size
}