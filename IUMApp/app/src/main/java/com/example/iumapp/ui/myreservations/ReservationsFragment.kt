package com.example.iumapp.ui.myreservations

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Done
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.iumapp.MainActivity
import com.example.iumapp.R
import com.example.iumapp.database.MyDbFactory
import com.example.iumapp.database.reservation.Reservation
import com.example.iumapp.databinding.FragmentReservationsBinding
import com.example.iumapp.ui.components.MyCard
import com.example.iumapp.ui.components.StyledIconButton
import com.example.iumapp.ui.components.TitleText
import com.example.iumapp.ui.login.LoginActivity
import com.example.iumapp.ui.teacherchoice.getActivity
import com.example.iumapp.ui.teacherchoice.myDb

private lateinit var userType: String
private var reservationList = mutableListOf<Reservation>()
@SuppressLint("StaticFieldLeak")
lateinit var saveContext: Context

class ReservationsFragment : Fragment() {

    private var _binding: FragmentReservationsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this)[ReservationsViewModel::class.java]

        _binding = FragmentReservationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textMyReservation
        val reservationListView: ComposeView = binding.reservationList

        userType = (activity as MainActivity).getUserType()
        reservationList = MyDbFactory
            .getMyDbInstance()
            .reservationDao()
            .provideReservationByUser(userType)
            .toMutableStateList()

        dashboardViewModel.text.observe(viewLifecycleOwner) {
            if ((activity as MainActivity).getUserType() == "guest") {
                textView.visibility = View.VISIBLE
            } else {
                reservationListView.visibility = View.VISIBLE
            }
        }

        reservationListView.setContent {
            ComposeReservationsList(reservationList as SnapshotStateList)
        }

        return root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        saveContext = context
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

@Composable
fun ComposeReservationsList(reservationList: SnapshotStateList<Reservation>) {
    Column(
        modifier = Modifier.padding(bottom = 56.dp)
    ) {
        Row(
            modifier = Modifier.padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(40.dp)

        ){
            TitleText(
                textVal = "Benvenuto \n$userType",
                fontWeight = FontWeight.ExtraBold
            )

            ExtendedFloatingActionButton(
                modifier = Modifier.padding(vertical = 12.dp, horizontal = 8.dp),
                onClick = {
                    startActivity(
                        saveContext,
                        Intent(saveContext, LoginActivity::class.java),
                        null
                    )
                    saveContext.getActivity()?.finish()
                },
                text = {
                    Text(
                        text = "Logout",
                    )
                },
                backgroundColor = MaterialTheme.colors.primary,
                icon = {
                    Icon(
                        imageVector = Icons.Filled.Logout,
                        contentDescription = "Logout",
                    )
                }
            )
        }

        TitleText(
            textVal = "Le tue prenotazioni:",
            fontWeight = FontWeight.ExtraBold
        )

        LazyColumn {
            items(items = reservationList) { reservationList ->
                SetReservationCard(reservation = reservationList)
            }
        }
    }
}

@Composable
fun SetReservationCard(reservation: Reservation){
    MyCard(
        backgroundColor = Color.White
    ) {
        CardContent(reservation)
    }
}

@Composable
fun CardContent(reservation: Reservation){
    var expanded by remember { mutableStateOf(false) }
    val reservationState = remember { mutableStateOf(reservation.status)}

    Column {
        Row (
            modifier = Modifier
                .padding(12.dp)
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioLowBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )
        ){
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 15.dp, horizontal = 8.dp)
            ) {
                TitleText(reservation.lesson +
                        "\n${reservation.day}," +
                        " alle ${when(reservation.time_slot){
                            15 -> "15:00"
                            16-> "16:00"
                            17 -> "17:00"
                            else -> "18:00"
                        }}",
                    FontWeight.ExtraBold)
                if (expanded) {
                    TitleText("Docente: ${reservation.teacher}" +
                            "\n\nStato: ${reservationState.value}",
                        FontWeight.ExtraBold)
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
        if(expanded){
            Row (
                modifier = Modifier.padding(start = 50.dp, bottom = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ){
                StyledIconButton(
                    {
                        reservationState.value = "Disdetta"

                        myDb.reservationDao()
                            .updateReservation(
                                reservation.id,
                                "Disdetta"
                            )
                    },
                    "Disdici",
                    iconVector = Icons.Outlined.Delete,
                    iconDescription = "Cancel reservation"
                )
                StyledIconButton(
                    {
                        reservationState.value = "Effettuata"
                        myDb.reservationDao()
                            .updateReservation(
                                reservation.id,
                                "Effettuata"
                            )
                    },
                    "Effettuata",
                    iconVector = Icons.Outlined.Done,
                    iconDescription = "Mark as done"
                )
            }
        }
    }
}