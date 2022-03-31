package com.example.iumapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.iumapp.MainActivity
import com.example.iumapp.adapter.LessonAdapter
import com.example.iumapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        val root: View = binding.root
        val recyclerView: RecyclerView = binding.lessonList
        val textView: TextView = binding.textHome
        val horizontalScrollView: HorizontalScrollView = binding.daysScroll

        homeViewModel.text.observe(viewLifecycleOwner) {
            if ((activity as MainActivity).getUserType() == "guest") {
                textView.visibility = View.VISIBLE
            }
        }

        homeViewModel.myDataset.observe(viewLifecycleOwner) {
            if ((activity as MainActivity).getUserType() != "guest") {
                recyclerView.visibility = View.VISIBLE
                recyclerView.adapter = LessonAdapter(it)
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}