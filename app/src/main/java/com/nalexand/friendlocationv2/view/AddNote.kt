package com.nalexand.friendlocationv2.view

import android.app.Activity
import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.nalexand.friendlocationv2.NotesActivity
import com.nalexand.friendlocationv2.R
import com.nalexand.friendlocationv2.data.AppDatabase
import com.nalexand.friendlocationv2.data.Note
import kotlinx.android.synthetic.main.activity_notes.*
import java.util.*

fun startAddNoteView(activity: NotesActivity, db : AppDatabase, userId : String) {
    val alertBuilder = AlertDialog.Builder(activity)
    val dialogView = LayoutInflater.from(activity).inflate(R.layout.window_note_add, null)
    val inputHeader = dialogView.findViewById<EditText>(R.id.inputNoteHeader)
    val inputContent = dialogView.findViewById<EditText>(R.id.inputNoteContent)
    val addBtn = dialogView.findViewById<Button>(R.id.confirmAddNote)
    val cancelBtn = dialogView.findViewById<Button>(R.id.cancelAddNote)
    val dialog = alertBuilder.setView(dialogView).create()

    addBtn.setOnClickListener {
        if (inputHeader.text.toString().isNotEmpty()) {
            db.make().insertNote(Note(
                user_id = userId,
                note = inputContent.text.toString(),
                header = inputHeader.text.toString(),
                date = Calendar.getInstance().timeInMillis
            ))
            (activity.noteRecycler.adapter as NoteRecycleAdapter).updateData(db.make().getNotes(userId))
            Toast.makeText(activity, "Hello new note!", Toast.LENGTH_SHORT).show()
            activity.setResult(Activity.RESULT_OK)
            dialog.dismiss()
        }
        else {
            dialog.dismiss()
        }
    }

    cancelBtn.setOnClickListener {
        dialog.dismiss()
    }
    dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    dialog.show()
}