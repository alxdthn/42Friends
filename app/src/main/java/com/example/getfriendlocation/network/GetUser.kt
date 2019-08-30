package com.example.getfriendlocation.network

import android.util.Log
import com.example.getfriendlocation.MainActivity
import com.example.getfriendlocation.data.AppDatabase
import com.example.getfriendlocation.data.UserLocationEntity
import com.example.getfriendlocation.view.ViewAdapter
import kotlinx.android.synthetic.main.activity_main.*

suspend fun getUser(activity: MainActivity, login: String, db: AppDatabase) : Int {

    Log.d("bestTAG", "fun \"getUser:\"")

    val response: Array<User>?

    if (db.make().getByLogin(login) != null) {
        Log.d("bestTAG", "user already exists")
        return 1
    }
    try {
        val token = activity.service.getToken(activity.requestBody).body()
        response = activity.service.getUser(
            "${token?.token_type} ${token?.access_token}", login
        ).body()
        if (response == null) return 3
    } catch (e: Throwable) {
        return 2
    }
    val user: User
    try {
        user = response[0]
    } catch (e: Throwable) { return 3 }
    Log.d("bestTAG", "end FIND!")
    Log.d("bestTAG", "login: ${user.login} id: ${user.id}")
    db.make().insert(UserLocationEntity(user.id, user.login, user.url))
    val values = db.make().getAll()
    activity.runOnUiThread {
        (activity.myRecycler.adapter as ViewAdapter).updateData(values)
    }
    return (0)
}
