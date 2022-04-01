package com.example.iumapp.ui.myreservations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ReservationsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is reservations Fragment"
    }
    val text: LiveData<String> = _text
}