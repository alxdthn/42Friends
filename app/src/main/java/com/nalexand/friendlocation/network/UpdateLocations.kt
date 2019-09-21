package com.nalexand.friendlocation.network

import com.nalexand.friendlocation.MainActivity
import com.nalexand.friendlocation.data.UserEntity
import kotlinx.coroutines.delay

suspend fun updateLocations(activity: MainActivity) : Int {
 //   try {
        val users = activity.db.make().getAll()
        val token = getToken(activity, activity.db)
        val listOfUsersId = mutableListOf<String>()
        val query : String

        for (user in users) {
            listOfUsersId.add(user.user_id)
        }
        query = listOfUsersId.joinToString(",")
        val response = activity.service.getUserLocation(
        "${token?.type} ${token?.value}", query, "false").body()
        delay(500)
        if (response != null) {
            for (user in response) {
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
//    } catch (e: Throwable) { return 1 }
    return 0
}