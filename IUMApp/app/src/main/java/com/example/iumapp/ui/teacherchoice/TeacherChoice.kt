package com.example.iumapp.ui.teacherchoice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.iumapp.ui.teacherchoice.ui.theme.IUMAppTheme


//TODO -> set column to contain:
// enable Toolbar with back button
// lesson name
// lesson description (TBD)
// teacher checklist (pretty cool, chose one at least -> constrain)
// confirm button
// cancel button (go back to main)


class TeacherChoice : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IUMAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting(intent.getStringExtra("lesson").toString())
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = name)
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    IUMAppTheme {
        Greeting("Algoritmi")
    }
}