package com.example.iumapp.ui.teacherchoice

import android.content.Context
import android.content.ContextWrapper
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.iumapp.R
import com.example.iumapp.database.MyDb
import com.example.iumapp.database.MyDbFactory
import com.example.iumapp.database.reservation.Reservation
import com.example.iumapp.ui.components.BodyText
import com.example.iumapp.ui.components.MyCard
import com.example.iumapp.ui.components.StyledIconButton
import com.example.iumapp.ui.components.TitleText
import kotlin.properties.Delegates

//TODO
// lesson description in relations !! (TBD)
// pass correct arguments for Reservation
// extract verbose code (onClicks for example) for better readability

lateinit var lessonName: String
lateinit var userName: String
lateinit var dayName: String
var timeSlot by Delegates.notNull<Int>()
lateinit var myDb: MyDb

class TeacherChoice : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lessonName = intent.getStringExtra("lesson").toString()
        userName = intent.getStringExtra("user").toString()
        dayName = intent.getStringExtra("day").toString()
        timeSlot = intent.getIntExtra("time_slot", 15)
        myDb = MyDbFactory.getMyDbInstance()

        setContent {
            SetContainer(lessonName, timeSlot)
        }
    }
}

fun calculateTimeSlotString(): String {
    return when(timeSlot){
        15 -> "15:00 - 16:00"
        16 -> "16:00 - 17:00"
        17 -> "17:00 - 18:00"
        else -> "18:00 - 19:00"
    }
}

fun Context.getActivity(): ComponentActivity? = when (this) {
    is ComponentActivity -> this
    is ContextWrapper -> baseContext.getActivity()
    else -> null
}

@Composable
fun SetContainer(lessonName: String, timeSlot: Int){
    val context = LocalContext.current
    val teachers = myDb
        .teachingDao()
        .getTeacherByLesson(lessonName)
        .toMutableList()

    for (teacher in myDb
        .reservationDao()
        .getUnavailableTeacher(lessonName, dayName, timeSlot)
    ){
        teachers.remove(teacher)
    }

    val chosenTeacher = remember { mutableStateOf("")}

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = "Scegli un docente") },
                navigationIcon = {
                    IconButton(onClick = { context.getActivity()?.finish() }) {
                        Icon(imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back",
                        )
                    }
                }
            )
        }
    ) {
        Column {
            MyCard(
                backgroundColor = Color.White
            ) {
                CardContent(lessonName = lessonName, timeSlot)
            }

            MyCard(
                backgroundColor = Color.White
            ) {
                Column(
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                ) {
                    ComposeScrollerSet(teachers = teachers, chosenTeacher = chosenTeacher)
                    TitleText(if (chosenTeacher.value != ""){
                        "Docente selezionato: \n${chosenTeacher.value}"
                    } else {
                        "Nessun docente selezionato"
                    }, FontWeight.Medium)

                    Row (
                        modifier = Modifier.padding(start = 30.dp, bottom = 15.dp),
                        horizontalArrangement = Arrangement.spacedBy(25.dp)
                    ){
                        StyledIconButton(
                            onClickFun = {
                                shortToast(
                                    context,
                                    "Prenotazione annullata!"
                                )
                                context.getActivity()?.finish()
                            },
                            textString = "Annulla",
                            iconVector = Icons.Filled.Delete,
                            iconDescription = "Delete reservation"
                        )

                        StyledIconButton(
                            onClickFun = {
                                if (chosenTeacher.value != ""){
                                    myDb
                                        .reservationDao()
                                        .insert(Reservation(
                                            lesson = lessonName,
                                            teacher = chosenTeacher.value,
                                            user = userName,
                                            day = dayName,
                                            time_slot = timeSlot,
                                            status = "Attiva"
                                        ))
                                    shortToast(
                                        context,
                                        "Prenotazione salvata!"
                                    )
                                    context.getActivity()?.finish()
                                } else {
                                    shortToast(
                                        context,
                                        "Nessun docente selezionato"
                                    )
                                }
                            },
                            textString = "Conferma",
                            iconVector = Icons.Filled.Done,
                            iconDescription = "Save reservation"
                        )
                    }
                }
            }
        }
    }
}

fun shortToast(context: Context, text: String){
    Toast.makeText(
        context,
        text,
        Toast.LENGTH_SHORT
    ).show()
}

@Composable
fun SetTeacherButton(teacherName: String, chosenTeacher: MutableState<String>){
    Button(
        onClick = { chosenTeacher.value = teacherName },
        shape = MaterialTheme.shapes.small.copy(CornerSize(percent = 50))
    ) {
        Text(text = teacherName)
    }
}

@Composable
private fun CardContent(lessonName: String, timeSlot: Int) {
    var expanded by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .padding(12.dp)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(4.dp)
        ) {

            TitleText(calculateTimeSlotString(),
                FontWeight.Medium)

            TitleText(lessonName,
                FontWeight.ExtraBold)

            if (expanded) {
                BodyText(
                    myDb
                        .lessonDao()
                        .getDescription(lessonName),
                    FontWeight.Bold
                )
            }
        }
        IconButton(onClick = { expanded = !expanded }) {
            Icon(
                imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                contentDescription = if (expanded) {
                    stringResource(R.string.show_less)
                } else {
                    stringResource(R.string.show_more)
                }
            )
        }
    }
}

@Composable
fun ComposeScrollerSet(teachers: List<String>, chosenTeacher: MutableState<String>) {
    val state = rememberScrollState()
    Row(
        modifier = Modifier
            .horizontalScroll(state)
            .padding(vertical = 20.dp, horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (teacher in teachers) {
            SetTeacherButton(teacherName = teacher, chosenTeacher = chosenTeacher)
            Spacer(modifier = Modifier.size(10.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SetContainer("Algoritmi e strutture dati", 1)
}