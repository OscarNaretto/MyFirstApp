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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.iumapp.MainActivity
import com.example.iumapp.database.MyDbFactory
import com.example.iumapp.databinding.FragmentHomeBinding
import com.example.iumapp.ui.teacherchoice.TeacherChoice
import com.example.iumapp.ui.teacherchoice.ui.theme.IUMAppTheme

class ReserveLessonFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private var lessonList = mutableListOf<String>()

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
        val recyclerView = binding.lessonList
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
                "monday",
                1,
                "attiva"
            ).toMutableStateList()


        daysScroller.setContent {
            IUMAppTheme {
                ComposeScrollerSet()
            }
        }

        recyclerView.setContent {
            IUMAppTheme {
                SetLessonList(lessonList as SnapshotStateList<String>)
            }
        }

        return root
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
            for (day in listOf("monday", "tuesday", "wednesday", "thursday", "friday")) {
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

    private fun dayButtonOnClickTest(dayName: String = "monday"){
        lessonList.clear()
        lessonList.addAll(listOf (when (dayName) {
            "monday" -> "Monkeyflip"
            "tuesday" -> "Chill"
            "thursday" -> "Sleep"
            "wednesday" -> "Eat"

            else -> "Sleep"
        }))
    }

    private fun dayButtonOnClick(dayName: String){
        lessonList.clear()
        lessonList.addAll(MyDbFactory
            .getMyDbInstance()
            .reservationDao()
            .provideAvailableLessons(
                MyDbFactory.getMyDbInstance(),
                dayName,
                1,
                "attiva"
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
        /*activity
            ?.supportFragmentManager
            ?.beginTransaction()
            ?.replace(R.id.nav_host_fragment_activity_main, TeacherChoiceFragment(), "findThisFragment")
            ?.addToBackStack(null)
            ?.commit()*/

        startActivity(
            Intent(activity as MainActivity, TeacherChoice::class.java)
                .putExtra("lesson", lessonName)
        )




    }

}