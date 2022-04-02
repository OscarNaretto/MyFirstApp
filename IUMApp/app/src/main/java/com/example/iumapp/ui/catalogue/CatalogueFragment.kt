package com.example.iumapp.ui.catalogue

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.iumapp.R
import com.example.iumapp.database.MyDbFactory

class CatalogueFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                Greetings()
            }
        }
    }

    @Composable
    private fun Greetings(lessons: List<String> = MyDbFactory
                                      .getMyDbInstance()
                                      .lessonDao()
                                      .getAll())
    {
        LazyColumn(modifier = Modifier.padding(top = 4.dp, bottom = 56.dp)) {
            items(items = lessons) { lessons ->
                Greeting(name = lessons)
            }
        }
    }

    @Composable
    private fun Greeting(name: String) {
        Card(
            backgroundColor = MaterialTheme.colors.primary,
            modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
            shape = RoundedCornerShape(30.dp),
        ) {
            CardContent(name)
        }
    }

    @Composable
    private fun CardContent(name: String) {
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
                    text = name,
                    style = MaterialTheme.typography.h6.copy(
                        fontWeight = FontWeight.ExtraBold
                    )

                )
                if (expanded) {
                    Text(
                        text = MyDbFactory
                            .getMyDbInstance()
                            .teachingDao()
                            .getTeacherByLesson(name)
                            .map { "\n".plus(it) }
                            .toString()
                            .replace("[", "")
                            .replace("]", ""),
                        style = MaterialTheme.typography.body2.copy(
                            fontWeight = FontWeight.Bold
                        )

                    )
                }
            }
            IconButton(onClick = { expanded = !expanded }) {
                Icon(
                    imageVector = if (expanded) Filled.ExpandLess else Filled.ExpandMore,
                    contentDescription = if (expanded) {
                        stringResource(R.string.show_less)
                    } else {
                        stringResource(R.string.show_more)
                    }
                )
            }
        }
    }
}

