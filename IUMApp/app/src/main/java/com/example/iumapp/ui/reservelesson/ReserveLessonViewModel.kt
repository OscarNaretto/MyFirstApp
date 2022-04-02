package com.example.iumapp.ui.reservelesson

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.iumapp.database.MyDbFactory
import com.example.iumapp.database.lesson.Lesson

class ReserveLessonViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "Login to unlock all features"
    }
    val text: LiveData<String> = _text

    private var _myDataset = MutableLiveData<List<String>>().apply{
        value = MyDbFactory
            .getMyDbInstance()
            .lessonDao()
            .getAll()
    }
    val myDataset: LiveData<List<String>> = _myDataset
}