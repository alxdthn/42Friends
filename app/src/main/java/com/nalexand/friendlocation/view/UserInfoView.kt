package com.nalexand.friendlocation.view

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.nalexand.friendlocation.MainActivity
import com.nalexand.friendlocation.R
import com.nalexand.friendlocation.data.AppDatabase
import com.nalexand.friendlocation.data.UserEntity
import kotlinx.android.synthetic.main.activity_main.*

fun 	startRemoveUserView(activity: MainActivity, db: AppDatabase, item: UserEntity) {

    val alertBuilder = AlertDialog.Builder(activity)
    val dialogView = LayoutInflater.from(activity).inflate(R.layout.window_user_remove, null)
    val dialog = alertBuilder.setView(dialogView).create()
    val delBtn = dialogView.findViewById<Button>(R.id.delUserBtn)

    delBtn.setOnClickListener {
        db.make().delete(item)
        (activity.myRecycler.adapter as ViewAdapter).updateData(db.make().getAll())
        Toast.makeText(activity, "User deleted", Toast.LENGTH_SHORT).show()
        if (db.make().getCount() == 0)
            activity.findViewById<TextView>(R.id.start).visibility = View.VISIBLE
        dialog.dismiss()
    }
    dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    dialog.show()
}
