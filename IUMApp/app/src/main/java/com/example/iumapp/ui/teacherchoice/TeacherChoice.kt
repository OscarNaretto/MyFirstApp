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
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.iumapp.database.MyDbFactory
import com.example.iumapp.database.reservation.Reservation


//TODO
// lesson description in relations !! (TBD)
// pass correct arguments for Reservation
// extract verbose code (onClicks for example) for better readability

class TeacherChoice : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val lessonName = intent.getStringExtra("lesson").toString()
        val timeSlot = intent.getIntExtra("time_slot", 1)

        setContent {
            SetContainer(lessonName, timeSlot)

        }
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
    val teachers = MyDbFactory
        .getMyDbInstance()
        .teachingDao()
        .getTeacherByLesson(lessonName)
        .toMutableList()

    for (teacher in MyDbFactory
        .getMyDbInstance()
        .reservationDao()
        .getUnavailableTeacher(lessonName, "Lunedì", timeSlot)
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
            Card(
                shape = RoundedCornerShape(30.dp),
                modifier = Modifier.padding(top = 10.dp, start = 8.dp, end = 8.dp),
                backgroundColor = Color.White,
                elevation = 100.dp
            ) {
                CardContent(lessonName = lessonName, timeSlot)
            }
            Card(
                shape = RoundedCornerShape(30.dp),
                modifier = Modifier.padding(vertical = 10.dp, horizontal = 8.dp),
                backgroundColor = Color.White,
                elevation = 100.dp
            ) {
                Column {
                    ComposeScrollerSet(teachers = teachers, chosenTeacher = chosenTeacher)
                    Text(
                        text = if (chosenTeacher.value != ""){
                            "Docente selezionato: \n ${chosenTeacher.value}"
                        } else {
                            "Nessun docente selezionato"
                        },
                        style = MaterialTheme.typography.h6.copy(
                            fontWeight = FontWeight.Medium
                        ),
                        modifier = Modifier.padding(bottom = 20.dp, start = 16.dp, end = 16.dp)
                    )
                }
            }

            Row (
                modifier = Modifier.padding(top = 5.dp)
            ){
                ExtendedFloatingActionButton(
                    backgroundColor = MaterialTheme.colors.primary,
                    modifier = Modifier.padding(start = 50.dp, end = 20.dp),
                    onClick = {
                        Toast.makeText(
                            context,
                            "Prenotazione annullata!",
                            Toast.LENGTH_SHORT
                        ).show()

                        context.getActivity()?.finish() },
                    icon = {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = "Delete reservation"
                        )
                    },
                    text = {
                        Text(text = "Annulla")
                    }
                )

                ExtendedFloatingActionButton(
                    backgroundColor = MaterialTheme.colors.primary,
                    onClick = {
                        if (chosenTeacher.value != ""){
                            MyDbFactory
                                .getMyDbInstance()
                                .reservationDao()
                                .insert(Reservation(
                                    lesson = lessonName,
                                    teacher = chosenTeacher.value,
                                    user = "oscar@gmail.com",
                                    day = "Lunedì", //TODO pass correct values!
                                    time_slot = timeSlot
                                ))
                            Toast.makeText(
                                context,
                                "Prenotazione salvata!",
                                Toast.LENGTH_SHORT
                            ).show()
                            context.getActivity()?.finish()
                        } else {
                            Toast.makeText(
                                context,
                                "Nessun docente selezionato",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Filled.Done,
                            contentDescription = "Save reservation"
                        )
                    },
                    text = {
                        Text(text = "Conferma")
                    }
                )
            }
        }
    }
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
            Text(
                text = "Turno 15:00 - 16:00 --- $timeSlot",
                style = MaterialTheme.typography.h6.copy(
                    fontWeight = FontWeight.Medium
                ),
                modifier = Modifier.padding(bottom = 20.dp)
            )

            Text(
                text = lessonName,
                style = MaterialTheme.typography.h6.copy(
                    fontWeight = FontWeight.ExtraBold
                ),
                modifier = Modifier.padding(bottom = 15.dp)
            )

            if (expanded) {
                Text(
                    text = "L’insegnamento ha lo scopo di introdurre i concetti e le tecniche fondamentali per l’analisi e la progettazione di algoritmi, che sono alla base dello sviluppo del software. Gli studenti acquisiranno conoscenze circa l’analisi di correttezza e complessità computazionale degli algoritmi, sulle strutture dati per la rappresentazione dell’informazione, sulle tecniche di problem-solving mediante lo sviluppo di algoritmi efficienti. L’insegnamento è supportato da un laboratorio che ne costituisce parte integrante, finalizzato alla realizzazione e sperimentazione degli algoritmi e delle strutture dati mediante un linguaggio imperativo ed uno object-oriented.",
                    style = MaterialTheme.typography.body1.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.padding(bottom = 10.dp)
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