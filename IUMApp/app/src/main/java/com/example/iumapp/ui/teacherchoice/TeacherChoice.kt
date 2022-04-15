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
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.iumapp.R
import com.example.iumapp.database.MyDb
import com.example.iumapp.database.MyDbFactory
import com.example.iumapp.database.reservation.Reservation
import com.example.iumapp.ui.components.*
import kotlin.properties.Delegates

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
            SetContainer(lessonName)
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
fun SetContainer(lessonName: String){
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

    val chosenTeacher = remember { mutableStateOf(teachers[0])}

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
                CardContent(lessonName = lessonName)
            }

            MyCard(
                backgroundColor = Color.White
            ) {
                Column(
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    MyClickableBoxRow(
                        chips = teachers,
                        current = chosenTeacher
                    )
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
private fun CardContent(lessonName: String) {
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