package com.nalexand.friendlocation

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.nalexand.friendlocation.data.AppDatabase
import com.nalexand.friendlocation.data.UserEntity
import com.nalexand.friendlocation.network.RetrofitFactory
import com.nalexand.friendlocation.network.TokenRequest
import com.nalexand.friendlocation.network.updateLocations
import com.nalexand.friendlocation.view.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    val service = RetrofitFactory.makeService()
    var requestBody = TokenRequest()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d("bestTAG", "starting!")

        val db = AppDatabase.invoke(this)
        val addUser = findViewById<Button>(R.id.addUser)

        addUser.setOnClickListener {
            Log.d("bestTAG", "click on add user")
            startAddUserView(this, db)
        }

        if (db.make().getCount() == 0)
            findViewById<TextView>(R.id.start).visibility = View.VISIBLE

        myRecycler.adapter = ViewAdapter(
            db.make().getAll(), db,
            object : ViewAdapter.Callback {
                override fun onItemLongClicked(item: UserEntity) {
                    Log.d("bestTAG", "hold!")
                    startRemoveUserView(this@MainActivity, db, item)
                }
                override fun onNotesClicked(item: UserEntity) {
                    Log.d("bestTAG", "notes")
                    startNoteActivity(this@MainActivity, item.user_id)
                }
            }
        )

        val swipeContainer: SwipeRefreshLayout = findViewById(R.id.refresh)

        swipeContainer.setOnRefreshListener {
            CoroutineScope(Dispatchers.IO).launch {
                Log.d("bestTAG", "refresh!")
                val ret = updateLocations(
                    this@MainActivity, db, swipeContainer
                )
                runOnUiThread {
                    swipeContainer.isRefreshing = false
                    when (ret) {
                        0 -> (myRecycler.adapter as ViewAdapter).updateData(db.make().getAll())
                        1 -> Toast.makeText(this@MainActivity, "No internet connection", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        swipeContainer.setColorSchemeColors(
            ContextCompat.getColor(this, R.color.colorPrimaryLight),
            ContextCompat.getColor(this, R.color.colorPrimaryLight),
            ContextCompat.getColor(this, R.color.colorPrimaryLight),
            ContextCompat.getColor(this, R.color.colorPrimaryLight)
        )
        swipeContainer.setProgressBackgroundColorSchemeColor(
            ContextCompat.getColor(this, R.color.colorPrimaryDark)
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val db = AppDatabase.invoke(this)

        if (resultCode == Activity.RESULT_OK) {
            (myRecycler.adapter as ViewAdapter).updateData(db.make().getAll())
        }
    }
}