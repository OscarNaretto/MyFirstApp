package com.example.dbtest.database

import android.content.Context

object MyDbFactory {
    var myDb: MyDb? = null

    fun getMyDbInstance(context: Context): MyDb{
        if (myDb == null) {
            myDb = MyDbImpl().getDbInstance(context)
        }
        return myDb as MyDb
    }
}