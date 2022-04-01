package com.example.iumapp.ui.catalogue

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.iumapp.database.MyDbFactory
import com.example.iumapp.database.lesson.Lesson

class CatalogueViewModel : ViewModel(){
    private var _myDataset = MutableLiveData<List<String>>().apply{
        value = MyDbFactory
            .getMyDbInstance()
            .lessonDao()
            .getAll()
    }
    val myDataset: LiveData<List<String>> = _myDataset
}