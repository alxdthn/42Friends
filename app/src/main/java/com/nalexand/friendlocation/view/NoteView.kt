package com.nalexand.friendlocation.view

import android.content.Intent

import com.nalexand.friendlocation.MainActivity
import com.nalexand.friendlocation.NotesActivity

fun startNoteActivity(activity: MainActivity, userId: String) {

    val notesActivity = Intent(activity, NotesActivity::class.java)
    notesActivity.putExtra("user_id", userId)
    activity.startActivityForResult(notesActivity, 1)
}
