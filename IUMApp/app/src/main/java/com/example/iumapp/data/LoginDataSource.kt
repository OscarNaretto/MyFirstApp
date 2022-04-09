package com.example.iumapp.data

import com.example.iumapp.data.model.LoggedInUser
import com.example.iumapp.database.MyDbFactory
import java.io.IOException

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {

    fun login(username: String, password: String): Result<LoggedInUser> {
        try {
            val myDb = MyDbFactory.getMyDbInstance()

            if(myDb.userDao().searchValidLoginUser(username, password) != username) {
                throw IOException("Error logging in")
            }

            return Result.Success(LoggedInUser("oscar@gmail.com"))
        } catch (e: Throwable) {
            return Result.Error(IOException("Invalid credentials", e))
        }
    }
}