package com.example.iumapp.ui.myreservations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ReservationsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Login to unlock all features"
    }
    val text: LiveData<String> = _text
}