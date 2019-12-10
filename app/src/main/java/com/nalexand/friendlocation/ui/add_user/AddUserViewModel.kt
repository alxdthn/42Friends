package com.nalexand.friendlocation.ui.add_user

import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nalexand.friendlocation.base.BaseViewModel
import com.nalexand.friendlocation.errors.UserNotFound
import com.nalexand.friendlocation.model.local.LocalState
import com.nalexand.friendlocation.model.local.User
import com.nalexand.friendlocation.repository.IntraRepository
import com.nalexand.friendlocation.repository.app.AppPreferences
import com.nalexand.friendlocation.utils.AppConstants.ERROR_INPUT
import com.nalexand.friendlocation.utils.AppConstants.ERROR_NETWORK
import com.nalexand.friendlocation.utils.AppConstants.ERROR_USER
import com.nalexand.friendlocation.utils.AppConstants.SUCCESS
import com.nalexand.friendlocation.utils.AppConstants.USER_EXISTS
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class AddUserViewModel @Inject constructor(
	private val repository: IntraRepository,
	private val prefs: AppPreferences
): BaseViewModel(), TextWatcher {

	var input: CharSequence? = null

	val onHandleInput = PublishSubject.create<Int>()

	val onLoading = LocalState()

	private val _newUser = MutableLiveData<User>()
	val newUser: LiveData<User> = _newUser

	fun handleInput() {
		when {
			input.isNullOrBlank() -> {
				onHandleInput.onNext(ERROR_INPUT)
			}
			userExists() -> {
				onHandleInput.onNext(USER_EXISTS)
			}
			else -> {
				addUser()
			}
		}
	}

	private fun userExists(): Boolean {
		return repository.getUserFromDatabaseByLogin(input.toString()) != null
	}

	private fun addUser() {
		onLoading.start()
		repository.findUserInApi(input.toString())
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe ({ userResult ->
				onLoading.cancel()
				onHandleInput.onNext(SUCCESS)
				_newUser.value = userResult
			}) { error ->
				onLoading.cancel()
				when (error) {
					is UserNotFound -> onHandleInput.onNext(ERROR_USER)
					else -> onHandleInput.onNext(ERROR_NETWORK)
				}
			}.addTo(getComposite())
	}

	fun removeToken() {
		prefs.clearToken()
	}

	override fun initStartData() {}

	override fun afterTextChanged(s: Editable?) {}

	override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

	override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
		input = s
	}
}