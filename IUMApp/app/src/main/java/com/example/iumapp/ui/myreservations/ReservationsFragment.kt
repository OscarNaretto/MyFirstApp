package com.example.iumapp.ui.myreservations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.iumapp.MainActivity
import com.example.iumapp.database.MyDbFactory
import com.example.iumapp.database.reservation.Reservation
import com.example.iumapp.databinding.FragmentReservationsBinding

//TODO -> set column to contain:
// collapsed CardView for each reservation (only name showing, everything else collapsed; delete reservation button collapsed)
// logout button (maybe in Row with username? just better than nothing)
// opt: separate old reservation already "used" from new ones -> label is easy

private lateinit var userType: String
private var reservationList = mutableListOf<Reservation>()

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
            ViewModelProvider(this).get(ReservationsViewModel::class.java)

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

@Composable
fun ComposeReservationsList(reservationList: SnapshotStateList<Reservation>) {
    Column {
        Row {
            Text(
                text = "Benvenuto \n $userType !",
                style = MaterialTheme.typography.h6.copy(
                    fontWeight = FontWeight.ExtraBold
                ),
                modifier = Modifier.padding(bottom = 15.dp)
            )
            Button(
                onClick = { /*TODO*/ },
            ) {
                Icon(
                    imageVector = Icons.Filled.Logout,
                    contentDescription = "Logout"
                )
                Text(text = "LOGOUT")
            }
        }
        Text(
            text = "Le tue prenotazioni:",
            style = MaterialTheme.typography.h6.copy(
                fontWeight = FontWeight.ExtraBold
            ),
            modifier = Modifier.padding(bottom = 15.dp)
        )
        LazyColumn(modifier = Modifier.padding(top = 4.dp, bottom = 56.dp)) {
            items(items = reservationList) { reservationList ->
                SetReservationCard(reservation = reservationList)
            }
        }
    }
}

@Composable
fun SetReservationCard(reservation: Reservation){
    Card(
        shape = RoundedCornerShape(30.dp),
        modifier = Modifier.padding(top = 10.dp, start = 8.dp, end = 8.dp),
        backgroundColor = Color.White,
        elevation = 20.dp
    ) {
        Column {
            Text(
                text = reservation.lesson,
                style = MaterialTheme.typography.h6.copy(
                    fontWeight = FontWeight.ExtraBold
                ),
                modifier = Modifier.padding(vertical = 15.dp, horizontal = 8.dp)
            )

            Text(
                text = "Il giorno ${reservation.day}, turno ${reservation.time_slot}",
                style = MaterialTheme.typography.h6.copy(
                    fontWeight = FontWeight.ExtraBold
                ),
                modifier = Modifier.padding(bottom = 15.dp)
            )

            Text(
                text = "Docente: ${reservation.teacher}",
                style = MaterialTheme.typography.h6.copy(
                    fontWeight = FontWeight.ExtraBold
                ),
                modifier = Modifier.padding(bottom = 15.dp)
            )

            Row {
                ExtendedFloatingActionButton(
                    backgroundColor = Color.Red,
                    modifier = Modifier.padding(start = 50.dp, end = 20.dp),
                    onClick = {
                        MyDbFactory
                            .getMyDbInstance()
                            .reservationDao()
                            .delete(reservation)

                        reservationList.remove(reservation)

                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Outlined.Delete,
                            contentDescription = "Delete reservation"
                        )
                    },
                    text = {
                        Text(
                            text = "Cancella",
                            color = Color.White
                        )
                    }
                )
            }
        }
    }
}