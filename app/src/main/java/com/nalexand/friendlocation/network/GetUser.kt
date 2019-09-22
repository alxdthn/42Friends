package com.nalexand.friendlocation.network

import android.util.Log
import com.nalexand.friendlocation.MainActivity
import com.nalexand.friendlocation.data.AppDatabase
import com.nalexand.friendlocation.data.UserEntity
import com.nalexand.friendlocation.view.ViewAdapter
import kotlinx.android.synthetic.main.activity_main.*

suspend fun getUser(activity: MainActivity, login: String, db: AppDatabase) : Int {

    Log.d("bestTAG", "fun \"getUser:\"")

    val response: Array<User>?
    val user: User

    if (login.isEmpty())
        return 4
    if (db.make().getByLogin(login) != null) {
        Log.d("bestTAG", "user already exists")
        return 1
    }
    try {
        val token = getToken(activity, db)
        response = activity.service.getUser(
            "${token?.type} ${token?.value}", login
        ).body()
        if (response == null)
            return 3
    } catch (e: Throwable) {
        return 2
    }
    try {
        user = response[0]
    } catch (e: Throwable) {
        return 3
    }
    Log.d("bestTAG", "end FIND!")
    Log.d("bestTAG", "login: ${user.login} id: ${user.id}")
    db.make().insert(
        UserEntity(
            user.id,
            user.login,
            user.url,
            end_at = "m"
        )
    )
    activity.runOnUiThread {
        (activity.myRecycler.adapter as ViewAdapter).updateData(db.make().getAll())
    }
    return (0)
}
