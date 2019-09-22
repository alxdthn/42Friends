package com.nalexand.friendlocation.network

import android.util.Log
import com.nalexand.friendlocation.MainActivity
import com.nalexand.friendlocation.data.UserEntity

suspend fun updateLocations(activity: MainActivity) : Int {
    try {
        val users = activity.db.make().getAll()
        val token = getToken(activity, activity.db)
        val listOfUsersId = mutableListOf<String>()
        val query : String

        for (user in users) {
            listOfUsersId.add(user.user_id)
            activity.db.make().update(
                UserEntity(
                    user.user_id,
                    user.login,
                    "-",
                    "",
                    "m"
                )
            )
        }
        query = listOfUsersId.joinToString(",")
        val response = activity.service.getUserLocation("${token?.type} ${token?.value}", query, "false")
        if (response.isSuccessful) {
            val responseBody = response.body()
            if (responseBody != null) {
                Log.d("bestTAG", "updated: ${responseBody.joinToString(",")}")
                for (user in responseBody) {
                    activity.db.make().update(
                        UserEntity(
                            user.user.id,
                            user.user.login,
                            user.host,
                            user.begin_at,
                            user.end_at ?: "a"
                        )
                    )
                }
            }
        }
        else {
            Log.d("bestTAG", "resp answer: ${response.message()}")
        }
    } catch (e: Throwable) { return 1 }
    return 0
}