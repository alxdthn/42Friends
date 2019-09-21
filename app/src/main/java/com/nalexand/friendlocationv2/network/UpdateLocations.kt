package com.nalexand.friendlocationv2.network

import com.nalexand.friendlocationv2.MainActivity
import com.nalexand.friendlocationv2.data.AppDatabase
import com.nalexand.friendlocationv2.data.UserEntity
import kotlinx.coroutines.delay

suspend fun updateLocations(activity: MainActivity, db: AppDatabase) : Int {
    try {
        val query = db.make().getAllLogins().joinToString(",")
        val response = activity.server.getUsers(query).body()
        delay(500)
        if (response != null) {
            for (user in response) {
                db.make().update(
                    UserEntity(
                        user.user_id,
                        user.login,
                        user.host,
                        user.begin_at,
                        user.end_at
                        )
                    )
                }
            }
        } catch (e: Throwable) {
            return 1
    }
    return 0
}