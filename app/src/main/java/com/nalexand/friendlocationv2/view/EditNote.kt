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

fun startEditNoteView(activity : NotesActivity, db : AppDatabase, note : Note) {
    val alertBuilder = AlertDialog.Builder(activity)
    val dialogView = LayoutInflater.from(activity).inflate(R.layout.window_note_edit, null)
    val editHeader = dialogView.findViewById<EditText>(R.id.editNoteHeader)
    val editContent = dialogView.findViewById<EditText>(R.id.editNoteContent)
    val acceptBtn = dialogView.findViewById<Button>(R.id.confirmEditNote)
    val cancelBtn = dialogView.findViewById<Button>(R.id.cancelEditNote)
    val dialog = alertBuilder.setView(dialogView).create()

    editHeader.setText(note.header)
    editContent.setText(note.note)

    editHeader.setSelection(note.header.length)
    editContent.setSelection(note.note.length)

    acceptBtn.setOnClickListener {
        if (editHeader.text.toString().isNotEmpty()) {
            db.make().updateNote(Note(
                note.id,
                note.user_id,
                editContent.text.toString(),
                editHeader.text.toString(),
                note.date
            ))
            (activity.noteRecycler.adapter as NoteRecycleAdapter).updateData(db.make().getNotes(note.user_id))
            Toast.makeText(activity, "Hello new note!", Toast.LENGTH_SHORT).show()
            activity.setResult(Activity.RESULT_OK)
            dialog.dismiss()
        }
        else {
            startRemoveNoteView(activity, db, note)
            dialog.dismiss()
        }
    }

    cancelBtn.setOnClickListener {
        dialog.dismiss()
    }
    dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    dialog.show()
}