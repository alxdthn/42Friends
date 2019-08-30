package com.example.getfriendlocation.view

import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.example.getfriendlocation.MainActivity
import com.example.getfriendlocation.R
import com.example.getfriendlocation.data.AppDatabase
import com.example.getfriendlocation.network.getUser
import android.widget.Button
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class 	AddUserView {

    fun show(activity: MainActivity, db: AppDatabase) {

        val alertBuilder = AlertDialog.Builder(activity)
        val dialogView = LayoutInflater.from(activity).inflate(R.layout.find_user_window, null)
        val input = dialogView.findViewById<EditText>(R.id.input_text)
        val findBtn = dialogView.findViewById<Button>(R.id.findBtn)
        val dialog = alertBuilder.setView(dialogView).create()

        findBtn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                CoroutineScope(Dispatchers.IO).launch {
                    val ret = getUser(activity, input.text.toString(), db)
                    activity.runOnUiThread {
                        when (ret) {
                            0 -> {
                                Toast.makeText(activity, "User added!", Toast.LENGTH_SHORT).show()
                                dialog.dismiss()
                            }
                            1 -> Toast.makeText(activity, "User already exist!", Toast.LENGTH_SHORT).show()
                            2 -> Toast.makeText(activity, "No internet connection", Toast.LENGTH_SHORT).show()
                            3 -> Toast.makeText(activity, "Can't find User", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        })
        dialog.show()
    }
}

