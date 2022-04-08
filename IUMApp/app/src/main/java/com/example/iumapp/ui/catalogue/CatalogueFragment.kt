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
import com.example.iumapp.ui.components.BodyText
import com.example.iumapp.ui.components.MyCard

class CatalogueFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                SetLessonList()
            }
        }
    }

    @Composable
    private fun SetLessonList(lessons: List<String> = MyDbFactory
                                      .getMyDbInstance()
                                      .lessonDao()
                                      .getAll())
    {
        LazyColumn(modifier = Modifier.padding(top = 4.dp, bottom = 56.dp)) {
            items(items = lessons) { lessons ->
                SetLessonItem(name = lessons)
            }
        }
    }

    @Composable
    private fun SetLessonItem(name: String) {
        MyCard {
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
                        dampingRatio = Spring.DampingRatioLowBouncy,
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
                    BodyText(
                        textVal = MyDbFactory
                            .getMyDbInstance()
                            .lessonDao()
                            .getDescription(name),
                        fontWeight = FontWeight.ExtraBold
                    )

                    BodyText(
                        textVal = "Docenti:" + MyDbFactory
                            .getMyDbInstance()
                            .teachingDao()
                            .getTeacherByLesson(name)
                            .map { "\n".plus(it) }
                            .toString()
                            .replace("[", "")
                            .replace("]", ""),
                        fontWeight = FontWeight.Bold
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

