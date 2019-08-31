package com.example.getfriendlocation.network

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.getfriendlocation.MainActivity
import com.example.getfriendlocation.data.AppDatabase
import com.example.getfriendlocation.data.UserLocationEntity
import com.example.getfriendlocation.view.ViewAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun updateLocations(activity: MainActivity, db: AppDatabase, swipe: SwipeRefreshLayout) {

    CoroutineScope(Dispatchers.IO).launch {
        val token = activity.service.getToken(activity.requestBody).body()
        delay(500)
        val users = db.make().getAll()

        for (user in users) {
            try {
                val response = activity.service.getUserLocation(
                    "${token?.token_type} ${token?.access_token}",
                    user.user_id
                ).body()?.first()
                delay(500)
                if (response != null) {
                    db.make().update(UserLocationEntity(
                        response.user.id,
                        response.user.login,
                        response.user.url,
                        response.begin_at,
                        response.campus_id,
                        response.end_at,
                        response.floor,
                        response.host,
                        response.id,
                        response.post,
                        response.primary,
                        response.row)
                    )
                }
            } catch (e: Throwable) { }
        }
        activity.runOnUiThread {
            swipe.isRefreshing = false
            (activity.myRecycler.adapter as ViewAdapter).updateData(db.make().getAll())
        }
    }
}