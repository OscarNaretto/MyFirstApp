package com.example.iumapp.ui.myreservations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.iumapp.MainActivity
import com.example.iumapp.databinding.FragmentReservationsBinding

//TODO -> set column to contain:
// collapsed CardView for each reservation (only name showing, everything else collapsed; delete reservation button collapsed)
// logout button (maybe in Row with username? just better than nothing)
// opt: separate old reservation already "used" from new ones

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

        val textView: TextView = binding.textDashboard

        dashboardViewModel.text.observe(viewLifecycleOwner) {
            if ((activity as MainActivity).getUserType() == "guest") {
                textView.visibility = View.VISIBLE
            }
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}