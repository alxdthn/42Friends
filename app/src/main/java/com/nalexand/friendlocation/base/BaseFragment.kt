package com.nalexand.friendlocation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.nalexand.friendlocation.main.App
import com.nalexand.friendlocation.main.MainActivity
import com.nalexand.friendlocation.ui.add_user.AddUserFragment
import com.nalexand.friendlocation.ui.home.HomeFragment
import com.nalexand.friendlocation.ui.user_details.UserDetailsFragment
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

abstract class BaseFragment<VM : BaseViewModel>(
	private val modelClass: Class<VM>,
	private val layout: Int
) : Fragment(),
	CompositeHolder {

	@Inject
	lateinit var viewModelFactory: ViewModelProvider.Factory

	lateinit var viewModel: VM

	lateinit var mainActivity: MainActivity

	private val compositeDisposable = CompositeDisposable()

	abstract fun initializeObservers()

	abstract fun initializeUi()

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		mainActivity = activity as MainActivity
		initViewModel()
		viewModel.commonViewModel = mainActivity.commonViewModel
		return inflater.inflate(layout, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		initializeUi()
		initializeObservers()
		viewModel.init()
	}

	private fun initViewModel() {
		inject()
		viewModel = ViewModelProviders.of(this, viewModelFactory).get(modelClass)
	}

	override fun getComposite(): CompositeDisposable = compositeDisposable

	private fun inject() {
		val injector = (mainActivity.application as App).getAppComponent()

		when (this) {
			is HomeFragment -> injector.inject(this)
			is UserDetailsFragment -> injector.inject(this)
			is AddUserFragment -> injector.inject(this)
		}
	}
}
