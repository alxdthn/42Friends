package com.nalexand.friendlocation.ui.home

import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import com.nalexand.friendlocation.R
import com.nalexand.friendlocation.base.BaseFragment
import com.nalexand.friendlocation.ui.add_user.AddUserFragment
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseFragment<HomeViewModel>(R.layout.fragment_home),
	View.OnClickListener {

	override fun initializeObservers() {

	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		fabAddUser.setOnClickListener(this)
	}

	override fun onClick(v: View?) {
		when (v?.id) {
			R.id.fabAddUser -> showAddUserDialog(v)
		}
	}

	private fun showAddUserDialog(v: View?) {
		v?.findNavController()?.navigate(R.id.nav_add_user)
	}
}

/*
    lateinit var service : RetrofitService
    lateinit var requestBody : TokenRequest
    lateinit var db : AppDatabase
    lateinit var addUser : Button
    lateinit var startView : TextView
    lateinit var swipeContainer : SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = AppDatabase.invoke(this)
        service = RetrofitFactory.makeService()
        requestBody = TokenRequest()

        addUser = findViewById(R.id.addUser)
        addUser.setOnClickListener {
            Log.d("bestTAG", "click on add user")
            startAddUserView(this)
        }

        startView = findViewById(R.id.start)
        if (db.make().getCount() == 0)
            startView.visibility = View.VISIBLE

        myRecycler.adapter = ViewAdapter(
            db.make().getAll(), db,
            object : ViewAdapter.Callback {
                override fun onItemLongClicked(item: UserEntity) {
                    Log.d("bestTAG", "hold!")
                    startRemoveUserView(this@MainActivity, item)
                }
                override fun onNotesClicked(item: UserEntity) {
                    Log.d("bestTAG", "notes")
                    startNoteActivity(this@MainActivity, item.user_id)
                }
            }
        )

        swipeContainer = findViewById(R.id.refresh)
        swipeContainer.setOnRefreshListener {
            CoroutineScope(Dispatchers.IO).launch {
                Log.d("bestTAG", "refresh!")
                val ret = updateLocations(this@MainActivity)
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
 */