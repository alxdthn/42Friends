package com.nalexand.friendlocation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.nalexand.friendlocation.data.AppDatabase
import com.nalexand.friendlocation.data.Note
import com.nalexand.friendlocation.view.*
import kotlinx.android.synthetic.main.activity_notes.*

class NotesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)

        val db = AppDatabase.invoke(this)
        val userId = intent.getStringExtra("user_id")

        Log.d("bestTAG", userId)

        noteRecycler.adapter = NoteRecycleAdapter(
            db.make().getNotes(userId),
            object : NoteRecycleAdapter.Callback {
                override fun onItemClicked(item: Note) {
                    Log.d("bestTAG", "click on note")
                    startEditNoteView(this@NotesActivity, db, item)
                }
                override fun onItemLongClicked(item: Note) {
                    Log.d("bestTAG", "long click on note")
                    startRemoveNoteView(this@NotesActivity, db, item)
                }
                override fun onAddButtonClicked() {
                    Log.d("bestTAG", "click on add btn")
                    startAddNoteView(this@NotesActivity, db, userId)
                }
            }
        )
    }
}
