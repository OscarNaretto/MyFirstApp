package com.example.iumapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.iumapp.R
import com.example.iumapp.database.lesson.Lesson

class LessonAdapter(
    private val context: Context,
    private val dataset: List<Lesson>
    ): RecyclerView.Adapter<LessonAdapter.LessonViewHolder>()  {

    class LessonViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.item_title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LessonViewHolder {
        return LessonViewHolder(
            LayoutInflater
            .from(parent.context)
            .inflate(R.layout.lesson_list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: LessonViewHolder, position: Int) {
        val item = dataset[position]
        holder.textView.text = item.name
    }

    override fun getItemCount() = dataset.size
}