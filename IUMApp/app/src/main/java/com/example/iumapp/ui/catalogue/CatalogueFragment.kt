package com.example.iumapp.ui.catalogue

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
import com.example.iumapp.databinding.FragmentCatalogueBinding

class CatalogueFragment: Fragment() {
    private var _binding: FragmentCatalogueBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val catalogueViewModel =
            ViewModelProvider(this).get(CatalogueViewModel::class.java)

        _binding = FragmentCatalogueBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val recyclerView: RecyclerView = binding.lessonCatalogue

        catalogueViewModel.myDataset.observe(viewLifecycleOwner) {
            recyclerView.adapter = LessonAdapter(it)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}