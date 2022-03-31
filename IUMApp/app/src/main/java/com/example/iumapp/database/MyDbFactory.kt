package com.example.iumapp.database

import android.content.Context

object MyDbFactory {
    var myDb: MyDb? = null

    fun initDb(context: Context){
        if (myDb == null) {
            myDb = MyDbImpl().getDbInstance(context)
        }
    }

    fun getMyDbInstance(): MyDb { return myDb as MyDb }
}