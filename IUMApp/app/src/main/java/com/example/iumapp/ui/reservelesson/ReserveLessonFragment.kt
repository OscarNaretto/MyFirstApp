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
import com.example.iumapp.database.MyDb
import com.example.iumapp.database.MyDbFactory
import com.example.iumapp.databinding.FragmentHomeBinding
import com.example.iumapp.ui.components.MyClickableCard
import com.example.iumapp.ui.components.StyledButton
import com.example.iumapp.ui.components.TitleText
import com.example.iumapp.ui.teacherchoice.TeacherChoice
import com.example.iumapp.ui.teacherchoice.dayName
import com.example.iumapp.ui.teacherchoice.timeSlot
import kotlin.properties.Delegates

class ReserveLessonFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private var lessonList = mutableListOf<String>()
    private var daysList = listOf(0,0,0,0,0).toMutableStateList()
    lateinit var recyclerView: ComposeView
    lateinit var myDb: MyDb
    private var currentDay: String = "Lunedì"

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
        myDb = MyDbFactory.getMyDbInstance()

        homeViewModel.text.observe(viewLifecycleOwner) {
            if ((activity as MainActivity).getUserType() == "guest") {
                binding.daysScroll.visibility = View.GONE
                textView.visibility = View.VISIBLE
            } else {
                recyclerView.visibility = View.VISIBLE
            }
        }

        lessonList = (listMapper(15)
                + listMapper(16)
                + listMapper(17)
                + listMapper(18))
            .toMutableStateList()

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
        lessonList = (listMapper(15)
                + listMapper(16)
                + listMapper(17)
                + listMapper(18))
            .toMutableStateList()

        recyclerView.setContent {
            SetLessonList(lessonList as SnapshotStateList<String>)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun listMapper(timeSlot: Int): List<String>{
        return myDb
            .reservationDao()
            .provideAvailableLessons(
                myDb,
                currentDay,
                timeSlot
            ).map {
                when(timeSlot){
                    15 -> "15:00 - 16:00\n$it"
                    16 -> "16:00 - 17:00\n$it"
                    17 -> "17:00 - 18:00\n$it"
                    else -> "18:00 - 19:00\n$it"
                }
            }
    }

    private fun daysButtonMapper(bName: String): Int{
        return when (bName){
            "Lunedì" -> 0
            "Martedì"-> 1
            "Mercoledì"-> 2
            "Giovedì"-> 3
            else -> 4
        }
    }

    private fun setDayButtonClicked(dayIndex: Int){
        daysList= listOf(0,0,0,0,0).toMutableStateList()
        daysList[dayIndex] = 1
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
        StyledButton(
            onClickFun = {
                setDayButtonClicked(daysButtonMapper(dayName))
                dayButtonOnClick(dayName)
        },
            textString = dayName.capitalize(),
            backgroundColor = if(daysList[daysButtonMapper(dayName)] == 0){
                MaterialTheme.colors.primary
            } else {
                MaterialTheme.colors.secondary
            }
        )
    }

    private fun dayButtonOnClick(dayName: String){
        currentDay = dayName
        lessonList.clear()
        lessonList.addAll(listMapper(15)
                + listMapper(16)
                + listMapper(17)
                + listMapper(18))
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
        MyClickableCard(
            CardContent = { CardContent(lessonName) },
            onClickAction = { launchTeacherChoiceFragment(lessonName) }
        )
    }

    @Composable
    private fun CardContent(name: String) {
        TitleText(
            textVal = name,
            fontWeight = FontWeight.ExtraBold
        )
    }

    private fun launchTeacherChoiceFragment(lessonName: String){
        val timeSlotChosen = when (lessonName.subSequence(0, 14) as String){
            "15:00 - 16:00\n" -> 15
            "16:00 - 17:00\n" -> 16
            "17:00 - 18:00\n" -> 17
            else -> 18
        }

        startActivity(
            Intent(activity as MainActivity, TeacherChoice::class.java)
                .putExtra("lesson", lessonName.substring(14))
                .putExtra("user", (activity as MainActivity).getUserType())
                .putExtra("day", currentDay)
                .putExtra("time_slot", timeSlotChosen)
        )
    }
}