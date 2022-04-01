package com.example.iumapp.ui.reservelesson

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.iumapp.MainActivity
import com.example.iumapp.adapter.LessonAdapter
import com.example.iumapp.database.lesson.Lesson
import com.example.iumapp.databinding.FragmentHomeBinding

class ReserveLessonFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel = ViewModelProvider(this)[ReserveLessonViewModel::class.java]
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        val root: View = binding.root
        val recyclerView: RecyclerView = binding.lessonList
        val textView: TextView = binding.textHome

        homeViewModel.text.observe(viewLifecycleOwner) {
            if ((activity as MainActivity).getUserType() == "guest") {
                binding.daysScroll.visibility = View.GONE
                textView.visibility = View.VISIBLE
            }
        }

        homeViewModel.myDataset.observe(viewLifecycleOwner) {
            if ((activity as MainActivity).getUserType() != "guest") {
                recyclerView.visibility = View.VISIBLE
                recyclerView.adapter = LessonAdapter(getProperLessons("monday"))
            }
        }

        binding.monday.setOnClickListener {
            recyclerView.adapter = LessonAdapter(getProperLessons("monday"))
        }
        binding.tuesday.setOnClickListener {
            recyclerView.adapter = LessonAdapter(getProperLessons("tuesday"))
        }
        binding.wednesday.setOnClickListener {
            recyclerView.adapter = LessonAdapter(getProperLessons("wednesday"))
        }
        binding.thursday.setOnClickListener {
            recyclerView.adapter = LessonAdapter(getProperLessons("thursday"))
        }
        binding.friday.setOnClickListener {
            recyclerView.adapter = LessonAdapter(getProperLessons("friday"))
        }

        return root
    }

    fun getProperLessons(dayName: String): List<Lesson> {
        //TODO proper query set, in order to return all Lesson obj minus already booked ones
        return listOf(Lesson(name = when (dayName){
            "monday"-> "Architetture"
            "tuesday"-> "DB"
            "wednesday"-> "SO"
            "thursday"-> "ASD"
            else -> "Un cazzo dai, è venerdì"
        }))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}