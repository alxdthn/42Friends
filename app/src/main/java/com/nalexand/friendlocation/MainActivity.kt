package com.nalexand.friendlocation

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
import com.nalexand.friendlocation.data.UserKey
import com.nalexand.friendlocation.data.UserLocationEntity
import com.nalexand.friendlocation.network.RetrofitFactory
import com.nalexand.friendlocation.network.TokenRequest
import com.nalexand.friendlocation.network.getUserKey
import com.nalexand.friendlocation.network.updateLocations
import com.nalexand.friendlocation.view.AddUserView
import com.nalexand.friendlocation.view.UserInfoView
import com.nalexand.friendlocation.view.ViewAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    val service = RetrofitFactory.makeService()
    var requestBody : TokenRequest? = null
    val db: AppDatabase? = null
    //de6ac060116057e0d3129a461d45ace116c3136da2b2e8778db39ab4fd6b53bd

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val key = data?.getStringExtra("result")
        db?.make()?.insertKey(UserKey(1, key))
        requestBody = TokenRequest(client_secret = key)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d("bestTAG", "starting!")

        val db = AppDatabase.invoke(this)
        val addUser = findViewById<Button>(R.id.addUser)
        getUserKey(this, db)

        addUser.setOnClickListener {
            Log.d("bestTAG", "click on add user")
            AddUserView(this).show(this, db)
        }

        if (db.make().getCount() == 0)
            findViewById<TextView>(R.id.start).visibility = View.VISIBLE

        myRecycler.adapter = ViewAdapter(
            db.make().getAll(),
            object : ViewAdapter.Callback {
                override fun onItemClicked(item: UserLocationEntity) {
                    Log.d("bestTAG", "hold!")
                    UserInfoView(this@MainActivity)
                        .show(this@MainActivity, db, item)
                }
            }
        )

        val swipeContainer: SwipeRefreshLayout = findViewById(R.id.refresh)

        swipeContainer.setOnRefreshListener {
            CoroutineScope(Dispatchers.IO).launch {
                Log.d("bestTAG", "refresh!")
                val ret = updateLocations(
                    this@MainActivity,
                    db,
                    swipeContainer
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
}