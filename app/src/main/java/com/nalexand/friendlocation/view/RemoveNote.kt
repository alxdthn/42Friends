package com.nalexand.friendlocation.view

import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.nalexand.friendlocation.R
import com.nalexand.friendlocation.repository.data.AppDatabase
import kotlinx.android.synthetic.main.fragment_notes.*
/*
fun 	startRemoveNoteView(activity: NotesActivity, db: AppDatabase, note: Note) {

    val alertBuilder = AlertDialog.Builder(activity)
    val dialogView = LayoutInflater.from(activity).inflate(R.layout.window_note_remove, null)
    val dialog = alertBuilder.setView(dialogView).create()
    val delBtn = dialogView.findViewById<Button>(R.id.delNoteBtn)
    val cancelDelBtn = dialogView.findViewById<Button>(R.id.cancelDelNoteBtn)
    val userId = note.user_id

    delBtn.setOnClickListener {
        db.make().deleteNote(note)
        (activity.noteRecycler.adapter as NoteRecycleAdapter).updateData(db.make().getNotes(userId))
        Toast.makeText(activity, "Goodbye note!", Toast.LENGTH_SHORT).show()
        activity.setResult(Activity.RESULT_OK)
        val test = db.make().getNotes(userId)
        dialog.dismiss()
    }
    cancelDelBtn.setOnClickListener {
        dialog.dismiss()
    }
    dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    dialog.show()
}
*/
