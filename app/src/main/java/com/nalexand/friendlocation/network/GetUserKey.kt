package com.nalexand.friendlocation.network

import android.content.Intent
import android.util.Log
import com.nalexand.friendlocation.MainActivity
import com.nalexand.friendlocation.data.AppDatabase
import com.nalexand.friendlocation.data.UserKey
import com.nalexand.friendlocation.view.AuthActivity

fun getUserKey(activity: MainActivity, db: AppDatabase) {

    val key: UserKey? = db.make().getKey()

    Log.d("keyTAG", "current key: ${key?.value ?: "empty"}")

    if (key == null) {
        Log.d("keyTAG", "try take new key")
        val authActivity = Intent(activity, AuthActivity::class.java)
        Log.d("keyTAG", "activity1")
        activity.startActivityForResult(authActivity, 1)
    }
    else {
        activity.requestBody = TokenRequest(client_secret = key.value)
    }
}