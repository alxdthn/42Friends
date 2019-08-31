package com.nalexand.friendlocation.network

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.nalexand.friendlocation.MainActivity
import com.nalexand.friendlocation.data.AppDatabase
import com.nalexand.friendlocation.data.UserLocationEntity
import kotlinx.coroutines.delay

suspend fun updateLocations(activity: MainActivity, db: AppDatabase, swipe: SwipeRefreshLayout) : Int {
    try {
        val users = db.make().getAll()
        val token = activity.service.getToken(activity.requestBody).body()
        delay(500)
        for (user in users) {
            val response = activity.service.getUserLocation(
            "${token?.token_type} ${token?.access_token}",
                user.user_id).body()?.first()
            delay(500)
            if (response != null) {
                db.make().update(
                    UserLocationEntity(
                        response.user.id,
                        response.user.login,
                        response.host,
                        response.begin_at,
                        response.end_at
                    )
                )
            }
        }
    } catch (e: Throwable) { return 1}
    return 0
}