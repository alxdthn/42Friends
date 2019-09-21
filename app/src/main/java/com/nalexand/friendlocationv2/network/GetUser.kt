package com.nalexand.friendlocationv2.network

import android.util.Log
import com.nalexand.friendlocationv2.MainActivity
import com.nalexand.friendlocationv2.data.AppDatabase
import com.nalexand.friendlocationv2.data.UserEntity
import com.nalexand.friendlocationv2.view.ViewAdapter
import kotlinx.android.synthetic.main.activity_main.*

suspend fun getUser(activity: MainActivity, login: String) : Int {

    Log.d("bestTAG", "fun \"getUser:\"")

    val response: Array<User>?
    val user: User

    if (login.isEmpty())
        return 4
    if (activity.db.make().getByLogin(login) != null) {
        Log.d("bestTAG", "user already exists")
        return 1
    }
    try {
        response = activity.server.getUsers(login).body()
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
    activity.db.make().insert(
        UserEntity(
            user.user_id,
            user.login,
            user.host,
            user.begin_at,
            user.end_at
        )
    )
    activity.runOnUiThread {
        (activity.myRecycler.adapter as ViewAdapter).updateData(activity.db.make().getAll())
    }
    return (0)
}
