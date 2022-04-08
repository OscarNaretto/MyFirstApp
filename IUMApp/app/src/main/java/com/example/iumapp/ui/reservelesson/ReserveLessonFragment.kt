package com.example.iumapp.ui.reservelesson

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.iumapp.MainActivity
import com.example.iumapp.database.MyDbFactory
import com.example.iumapp.databinding.FragmentHomeBinding
import com.example.iumapp.ui.teacherchoice.TeacherChoice

class ReserveLessonFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private var lessonList = mutableListOf<String>()
    lateinit var recyclerView: ComposeView


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
        recyclerView = binding.lessonList
        val textView: TextView = binding.textHome
        val daysScroller = binding.daysScroll

        homeViewModel.text.observe(viewLifecycleOwner) {
            if ((activity as MainActivity).getUserType() == "guest") {
                binding.daysScroll.visibility = View.GONE
                textView.visibility = View.VISIBLE
            } else {
                recyclerView.visibility = View.VISIBLE
            }
        }

        lessonList = MyDbFactory
            .getMyDbInstance()
            .reservationDao()
            .provideAvailableLessons(
                MyDbFactory.getMyDbInstance(),
                "Lunedì",
                1
            ).toMutableStateList()

        daysScroller.setContent {
            ComposeScrollerSet()

        }

        recyclerView.setContent {
            SetLessonList(lessonList as SnapshotStateList<String>)
        }

        return root
    }

    override fun onResume() {
        super.onResume()
        lessonList = MyDbFactory
            .getMyDbInstance()
            .reservationDao()
            .provideAvailableLessons(
                MyDbFactory.getMyDbInstance(),
                "Lunedì",
                1
            ).toMutableStateList()

        recyclerView.setContent {
            SetLessonList(lessonList as SnapshotStateList<String>)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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
            for (day in listOf("Lunedì", "Martedì", "Mercoledì", "Giovedì", "Venerdì")) {
                ProvideButton(dayName = day)
                Spacer(modifier = Modifier.size(10.dp))
            }
        }
    }

    @Composable
    fun ProvideButton(dayName: String) {
        ExtendedFloatingActionButton(
            onClick = { dayButtonOnClick(dayName) },
            text = { Text(dayName.capitalize()) },
            backgroundColor = MaterialTheme.colors.primary,
        )
    }

    private fun dayButtonOnClick(dayName: String){
        lessonList.clear()
        lessonList.addAll(MyDbFactory
            .getMyDbInstance()
            .reservationDao()
            .provideAvailableLessons(
                MyDbFactory.getMyDbInstance(),
                dayName,
                1
            )
        )
    }

    @Composable
    fun SetLessonList(lessons: SnapshotStateList<String>) {
        LazyColumn(modifier = Modifier.padding(vertical = 6.dp)) {
            items(items = lessons) { lessons ->
                SetLessonItem(lessonName = lessons)
            }
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    private fun SetLessonItem(lessonName: String) {
        Card(
            backgroundColor = MaterialTheme.colors.primary,
            modifier = Modifier
                .padding(vertical = 4.dp, horizontal = 8.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(30.dp),
            onClick = { launchTeacherChoiceFragment(lessonName) }
        ) {
            CardContent(lessonName)
        }
    }

    @Composable
    private fun CardContent(name: String) {
        Text(
            modifier = Modifier
                .padding(12.dp)
                .width(IntrinsicSize.Max),
            text = name,
            style = MaterialTheme.typography.h6.copy(
                fontWeight = FontWeight.ExtraBold
            )
        )
    }

    private fun launchTeacherChoiceFragment(lessonName: String){
        startActivity(
            Intent(activity as MainActivity, TeacherChoice::class.java)
                .putExtra("lesson", lessonName)
                .putExtra("time_slot", 1)
        )
    }
}