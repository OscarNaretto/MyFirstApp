package com.example.iumapp.ui.reservelesson

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.iumapp.MainActivity
import com.example.iumapp.adapter.LessonAdapter
import com.example.iumapp.database.MyDbFactory
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
        val composeScoller = binding.daysScroll

        homeViewModel.text.observe(viewLifecycleOwner) {
            if ((activity as MainActivity).getUserType() == "guest") {
                binding.daysScroll.visibility = View.GONE
                textView.visibility = View.VISIBLE
            }
        }

        homeViewModel.myDataset.observe(viewLifecycleOwner) {
            if ((activity as MainActivity).getUserType() != "guest") {
                recyclerView.visibility = View.VISIBLE
                dayButtonOnClick()
            }
        }

        composeScoller.setContent {
            ComposeScrollerSet()
        }

        return root
    }

    @Composable
    private fun ComposeScrollerSet() {
        val state = rememberScrollState()

        Row(
            modifier = Modifier
                .horizontalScroll(state)
                .padding(vertical = 20.dp, horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            for(day in listOf("monday","tuesday","wednesday","thursday","friday")){
                ProvideButton(dayName = day)
                Spacer(modifier = Modifier.size(10.dp))
            }
        }
    }

    @Composable
    fun ProvideButton(dayName: String){
        ExtendedFloatingActionButton(
            onClick = { dayButtonOnClick(dayName) },
            text = { Text(dayName.capitalize()) },
            backgroundColor = MaterialTheme.colors.primary,
        )
    }

    private fun dayButtonOnClick(dayName: String = "monday"){
        binding.lessonList.adapter = LessonAdapter(MyDbFactory
            .getMyDbInstance()
            .reservationDao()
            .provideAvailableLessons(
                MyDbFactory.getMyDbInstance(),
                dayName,
                15,
                "attiva"
            ))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}