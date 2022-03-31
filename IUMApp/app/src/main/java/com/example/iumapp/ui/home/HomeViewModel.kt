package com.example.iumapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.iumapp.database.MyDbFactory
import com.example.iumapp.database.lesson.Lesson

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    private val _myDataset = MutableLiveData<List<Lesson>>().apply{
        value = MyDbFactory
            .getMyDbInstance()
            .lessonDao()
            .getAll()
    }
    val myDataset: LiveData<List<Lesson>> = _myDataset
}