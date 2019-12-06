package com.nalexand.friendlocation.ui.home

import android.animation.ObjectAnimator
import android.graphics.Point
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.animation.doOnEnd
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.nalexand.friendlocation.R
import com.nalexand.friendlocation.base.BaseFragment
import com.nalexand.friendlocation.ui.home.adapter.UserAdapter
import com.nalexand.friendlocation.ui.home.adapter.UserItemsHandler
import com.nalexand.friendlocation.utils.AppAnimator.HIDE
import com.nalexand.friendlocation.utils.AppAnimator.SHOW
import com.nalexand.friendlocation.utils.extensions.observe
import com.nalexand.friendlocation.utils.extensions.showKeyboard
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.CompletableSubject
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_add_user.*
import kotlinx.android.synthetic.main.fragment_add_user.view.*
import kotlinx.android.synthetic.main.fragment_home.*
import java.math.BigInteger
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
			R.id.fabAddUser -> navigateToAddNewUser()
		}
	}

	private fun navigateToAddNewUser() {
		fabAddUser.translateX(300, 1, HIDE, getWidth()).mergeWith {
			val size = rvUsers.adapter?.itemCount ?: 0
			if (size > 0) {
				val viewsForAnimation = prepareViews(rvUsers, size)
				animate(viewsForAnimation, HIDE) {
					rvUsers.recycledViewPool.clear()
					findNavController().navigate(R.id.action_nav_home_to_nav_add_user)
				}
			} else {
				findNavController().navigate(R.id.action_nav_home_to_nav_add_user)
			}
		}.subscribe()
	}

	private fun animate(viewsForAnimation: List<AnimationParams>, type: Int, onComplete: () -> Unit) {
		val interval = Observable.interval(100, TimeUnit.MILLISECONDS)
		val viewsObservable = Observable.fromIterable(viewsForAnimation)
		val width = getWidth()

		Observable.zip(interval, viewsObservable,
			BiFunction<Long, AnimationParams, Disposable> { _, params ->
				params.run {
					view.translateX(duration, dir, type, width)
						.doOnComplete {
							if (last) onComplete()
						}
						.subscribe()
				}
			})
			.subscribeOn(AndroidSchedulers.mainThread())
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe()
	}

	private fun prepareViews(recycler: RecyclerView, size: Int): List<AnimationParams> {
		val viewsForAnimation = mutableListOf<AnimationParams>()

		for (pos in 0..size) {
			val holder = recycler.findViewHolderForAdapterPosition(pos)
			if (holder is UserAdapter.ViewHolder) {
				val even = pos % 2 == 0
				val params = AnimationParams(
					view = holder.itemView,
					duration = (500 - pos * 20).toLong(),
					dir = if (even) -1 else 1
				)
				viewsForAnimation.add(params)
			}
		}
		viewsForAnimation.last().last = true
		return viewsForAnimation
	}

	data class AnimationParams(
		val view: View,
		val duration: Long,
		val dir: Int,
		var last: Boolean = false
	)
}

fun Fragment.getWidth(): Float {
	val point = Point()
	val display = requireActivity().windowManager.defaultDisplay
	display?.getSize(point)
	return point.x.toFloat()
}

fun View.translatePos(duration: Long, windWidth: Float, dir: Int, type: Int): Completable {
	val animationSubject = CompletableSubject.create()
	val toXDelta = if (type == HIDE) {
		if (dir == -1) x - windWidth else windWidth - x
	} else {
		0f
	}

	return animationSubject.doOnSubscribe {
		ViewCompat.animate(this)
			.setDuration(duration)
			.translationX(toXDelta)
			.withEndAction {
				animationSubject.onComplete()
			}
	}
}

fun View.translateY(duration: Long, ) {
	ObjectAnimator.ofFloat(view, "translationY", toDeltaY).apply {
		this.duration = duration
		doOnEnd {
			if (view.id == R.id.cvAddUserInput) {
				view.edxAddUser.showKeyboard()
			}
		}
		start()
	}
}

object AnimType {
	const val HIDE_LEFT = 1
	const val HIDE_RIGHT = 2
	const val SHOW_LEFT = 3
	const val SHOW_RIGHT = 4
	const val HIDE_TOP = 5
	const val HIDE_BOTTOM = 6
	const val SHOW_TOP = 7
	const val SHOW_BOTTOM = 8
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