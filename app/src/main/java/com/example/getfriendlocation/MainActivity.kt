package com.example.getfriendlocation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.getfriendlocation.data.AppDatabase
import com.example.getfriendlocation.network.RetrofitFactory
import com.example.getfriendlocation.network.TokenRequest
import com.example.getfriendlocation.network.updateLocations
import com.example.getfriendlocation.view.AddUserView
import com.example.getfriendlocation.view.ViewAdapter
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    val service = RetrofitFactory.makeServiceForToken()

    val requestBody = TokenRequest(
        "client_credentials",
        "6d94ec75f2839e414dcba355566192b67ee05bc2d9a3da66aee8645343a4bffd",
        "de6ac060116057e0d3129a461d45ace116c3136da2b2e8778db39ab4fd6b53bd"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("bestTAG", "starting!")

        val db = AppDatabase.invoke(this)
        val addUser = findViewById<Button>(R.id.addUser)

        addUser.setOnClickListener {
            Log.d("bestTAG", "click on add user")
            AddUserView().show(this, db)
        }

        myRecycler.adapter = ViewAdapter(db.make().getAll())

        val swipeContainer: SwipeRefreshLayout = findViewById(R.id.refresh)

        swipeContainer.setOnRefreshListener {
            Log.d("bestTAG", "refresh!")
            updateLocations(this, db)
            swipeContainer.isRefreshing = false
        }
    }
}