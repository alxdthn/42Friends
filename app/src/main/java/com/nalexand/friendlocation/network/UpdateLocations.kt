package com.nalexand.friendlocation.network

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.nalexand.friendlocation.MainActivity
import com.nalexand.friendlocation.data.AppDatabase
import com.nalexand.friendlocation.data.UserEntity
import kotlinx.coroutines.delay

suspend fun updateLocations(activity: MainActivity, db: AppDatabase, swipe: SwipeRefreshLayout) : Int {
    try {
        val users = db.make().getAll()
        val token = getToken(activity, db)
        for (user in users) {
            val response = activity.service.getUserLocation(
            "${token?.type} ${token?.value}",
                user.user_id).body()?.first()
            delay(500)
            if (response != null) {
                db.make().update(
                    UserEntity(
                        response.user.id,
                        response.user.login,
                        response.host,
                        response.begin_at,
                        response.end_at ?: "a"
                    )
                )
            }
        }
    } catch (e: Throwable) { return 1}
    return 0
}