package com.nalexand.friendlocation.view

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.nalexand.friendlocation.MainActivity
import com.nalexand.friendlocation.R
import com.nalexand.friendlocation.data.AppDatabase
import com.nalexand.friendlocation.data.UserLocationEntity
import kotlinx.android.synthetic.main.activity_main.*

class 	UserInfoView(activity: MainActivity) {

    private val alertBuilder = AlertDialog.Builder(activity)
    private val dialogView = LayoutInflater.from(activity).inflate(R.layout.user_info_window, null)
    private val dialog = alertBuilder.setView(dialogView).create()
    private val delBtn = dialogView.findViewById<Button>(R.id.delUserBtn)

    fun show(activity: MainActivity, db: AppDatabase, item: UserLocationEntity) {

        delBtn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                db.make().delete(item)
                (activity.myRecycler.adapter as ViewAdapter).updateData(db.make().getAll())
                Toast.makeText(activity, "User deleted", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
        })
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
    }
}
