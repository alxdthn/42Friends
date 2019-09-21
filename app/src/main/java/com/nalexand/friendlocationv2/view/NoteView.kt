package com.nalexand.friendlocationv2.view

import android.content.Intent

import com.nalexand.friendlocationv2.MainActivity
import com.nalexand.friendlocationv2.NotesActivity

fun startNoteActivity(activity: MainActivity, userId: String) {

    val notesActivity = Intent(activity, NotesActivity::class.java)
    notesActivity.putExtra("user_id", userId)
    activity.startActivityForResult(notesActivity, 1)
}
