package com.nalexand.friendlocation.ui.home

import android.util.Log
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.nalexand.friendlocation.R
import com.nalexand.friendlocation.base.BaseFragment
import com.nalexand.friendlocation.ui.home.adapter.UserAdapter
import com.nalexand.friendlocation.ui.home.adapter.UserItemsHandler
import com.nalexand.friendlocation.utils.animator.AnimParams
import com.nalexand.friendlocation.utils.animator.AnimType.HIDE_LEFT
import com.nalexand.friendlocation.utils.animator.AnimType.HIDE_RIGHT
import com.nalexand.friendlocation.utils.animator.translatePos
import com.nalexand.friendlocation.utils.extensions.itemCount
import com.nalexand.friendlocation.utils.extensions.observe
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.concurrent.TimeUnit


class HomeFragment : BaseFragment<HomeViewModel>(R.layout.fragment_home),
	View.OnClickListener {

	lateinit var itemsHandler: UserItemsHandler

	override fun initializeUi() {
		itemsHandler = UserItemsHandler(this, UserAdapter())
		rvUsers.adapter = itemsHandler.adapter
		fabAddUser.setOnClickListener(this)
	}

	override fun initializeObservers() {
		Log.d("bestTAG", "init obs")
		observe(viewModel.users) { users ->
			Log.d("bestTAG", users.joinToString { it.login })
			itemsHandler render users
		}
	}

	override fun onClick(v: View?) {
		when (v?.id) {
			R.id.fabAddUser -> {
				prepareAnimForNavigateToAddNewUser {
					findNavController().navigate(R.id.action_nav_home_to_nav_add_user)
				}
			}
		}
	}

	private fun prepareAnimForNavigateToAddNewUser(onAnimationComplete: () -> Unit) {
		fabAddUser.translatePos(300, HIDE_RIGHT).mergeWith {
			val size = rvUsers.itemCount()
			if (size > 0) {
				animate(prepareParams(rvUsers, size), onAnimationComplete)
			} else {
				onAnimationComplete()
			}
		}.subscribe()
	}

	private fun animate(viewsForAnimation: List<AnimParams>, onComplete: () -> Unit) {
		val interval = Observable.interval(100, TimeUnit.MILLISECONDS)
		val viewsObservable = Observable.fromIterable(viewsForAnimation)

		Observable.zip(interval, viewsObservable,
			BiFunction<Long, AnimParams, Disposable> { _, params ->
				params.run {
					view.translatePos(duration, type)
						.doOnComplete {
							if (last) onComplete()
						}
						.subscribeOn(AndroidSchedulers.mainThread())
						.subscribe()
				}
			})
			.subscribe {
				Log.d("bestTAG", "animEnd")
			}.addTo(composite)
	}

	private fun prepareParams(recycler: RecyclerView, size: Int): List<AnimParams> {
		val viewsForAnimation = mutableListOf<AnimParams>()

		for (pos in 0..size) {
			val holder = recycler.findViewHolderForAdapterPosition(pos)
			if (holder is UserAdapter.ViewHolder) {
				val even = pos % 2 == 0
				val params = AnimParams(
					view = holder.itemView,
					duration = (500 - pos * 20).toLong(),
					type = if (even) HIDE_LEFT else HIDE_RIGHT
				)
				viewsForAnimation.add(params)
			}
		}
		viewsForAnimation.last().last = true
		return viewsForAnimation
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