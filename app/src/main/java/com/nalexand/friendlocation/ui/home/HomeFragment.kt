package com.nalexand.friendlocation.ui.home

import android.animation.ObjectAnimator
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.nalexand.friendlocation.R
import com.nalexand.friendlocation.base.BaseFragment
import com.nalexand.friendlocation.ui.home.adapter.UserAdapter
import com.nalexand.friendlocation.ui.home.adapter.UserItemsHandler
import com.nalexand.friendlocation.utils.extensions.observe
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseFragment<HomeViewModel>(R.layout.fragment_home),
	View.OnClickListener {

	lateinit var itemsHandler: UserItemsHandler

	override fun initializeUi() {
		itemsHandler = UserItemsHandler(this)
		rvUsers.adapter = itemsHandler.adapter
		fabAddUser.setOnClickListener(this)
	}

	override fun initializeObservers() {
		observe(viewModel.users) { users ->
			itemsHandler render users
		}
	}

	override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {
		if (nextAnim != 0) {
			val anim = AnimationUtils.loadAnimation(context, nextAnim)
			anim.setAnimationListener(
				HomeAnimationListener(
					enter,
					activity?.findViewById(R.id.rvUsers)
				)
			)
			return anim
		}
		return null
	}

	class HomeAnimationListener(private val enter: Boolean, private val rv: RecyclerView?) :
		Animation.AnimationListener {
		override fun onAnimationRepeat(animation: Animation?) {

		}

		override fun onAnimationEnd(animation: Animation?) {

		}

		override fun onAnimationStart(animation: Animation?) {
			if (!enter && rv != null) {
				val size = rv.adapter?.itemCount ?: 0
				for (pos in 0..size) {
					val holder = rv.findViewHolderForAdapterPosition(pos)
					if (holder is UserAdapter.ViewHolder) {
						translateX(holder.itemView)
					}
				}
			}
		}

		private fun translateX(view: View) {
			ObjectAnimator.ofFloat(view, "translationX", view.width.toFloat()).apply {
				duration = 500
				start()
			}
		}
	}

	override fun onClick(v: View?) {
		when (v?.id) {
			R.id.fabAddUser -> navigateToAddNewUser()
		}
	}

	private fun navigateToAddNewUser() {
		findNavController().navigate(R.id.action_nav_home_to_nav_add_user)
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