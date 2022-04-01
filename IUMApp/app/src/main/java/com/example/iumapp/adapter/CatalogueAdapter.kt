package com.example.iumapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.iumapp.R
import com.example.iumapp.database.MyDbFactory
import com.example.iumapp.database.lesson.Lesson
import kotlinx.coroutines.NonCancellable.start

class CatalogueAdapter(
    private val dataset: List<Lesson>
): RecyclerView.Adapter<CatalogueAdapter.CatalogueViewHolder>()  {

    class CatalogueViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val lessonView: TextView = view.findViewById(R.id.lesson_title)
        val teachersView: TextView = view.findViewById(R.id.teacher_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatalogueViewHolder {
        return CatalogueViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.catalogue_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CatalogueViewHolder, position: Int) {
        val item = dataset[position]
        holder.lessonView.text = item.name
        holder.teachersView.text = formatTeachersList(item.name)
    }

    private fun formatTeachersList(lessonName: String): String {
        return MyDbFactory
            .getMyDbInstance()
            .teachingDao()
            .getTeacherByLesson(lessonName)
            .map { "\n".plus(it) }
            .toString()
            .replace("[", "")
            .replace("]", "")
    }
    override fun getItemCount() = dataset.size
}