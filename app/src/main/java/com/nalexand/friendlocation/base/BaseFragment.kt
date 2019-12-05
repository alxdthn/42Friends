package com.nalexand.friendlocation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.nalexand.friendlocation.di.ViewModelUtil
import com.nalexand.friendlocation.main.MainActivity
import com.nalexand.friendlocation.main.App
import com.nalexand.friendlocation.ui.add_user.AddUserFragment
import com.nalexand.friendlocation.ui.home.HomeFragment
import com.nalexand.friendlocation.ui.notes.NotesFragment
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

abstract class BaseFragment<VM : BaseViewModel>(private val layout: Int) : Fragment() {

	@Inject
	protected lateinit var viewModel: VM

	lateinit var mainActivity: MainActivity

	val composite = CompositeDisposable()

	abstract fun initializeObservers()

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		mainActivity = activity as MainActivity
		inject()
		initializeObservers()
		return inflater.inflate(layout, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		val viewModelFactory = ViewModelUtil.createFor(viewModel)
		ViewModelProviders.of(this, viewModelFactory).get(viewModel.javaClass)
		viewModel.onViewCreated()
	}

	private fun inject() {
		val injector = (mainActivity.application as App).getAppComponent()

		when (this) {
			is HomeFragment -> injector.inject(this)
			is NotesFragment -> injector.inject(this)
			is AddUserFragment -> injector.inject(this)
		}
	}
}