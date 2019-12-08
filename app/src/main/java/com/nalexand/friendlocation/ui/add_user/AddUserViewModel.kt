package com.nalexand.friendlocation.ui.add_user

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nalexand.friendlocation.base.BaseViewModel
import com.nalexand.friendlocation.errors.UserNotFound
import com.nalexand.friendlocation.model.local.LocalState
import com.nalexand.friendlocation.model.local.User
import com.nalexand.friendlocation.repository.IntraRepository
import com.nalexand.friendlocation.repository.app.AppPreferences
import io.reactivex.rxkotlin.addTo
import io.reactivex.subjects.PublishSubject
import retrofit2.HttpException
import java.net.UnknownHostException
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

	companion object {
		const val ERROR_USER = 1
		const val SUCCESS = 3
		const val ERROR_INPUT = 2
		const val ERROR_NETWORK = 4
		const val USER_EXISTS = 5
	}

	fun handleInput() {
		when {
			input.isNullOrBlank() -> {
				Log.d("bestTAG", "ERROR_INPUT")
				onHandleInput.onNext(ERROR_INPUT)
			}
			userExists() -> {
				Log.d("bestTAG", "USER_EXISTS")
				onHandleInput.onNext(USER_EXISTS)
			}
			else -> {
				addUser()
			}
		}
	}

	private fun userExists(): Boolean {
		return repository.getUserByLogin(input.toString()) != null
	}

	private fun addUser() {
		onLoading.start()
		repository.findUserInApi(input.toString())
			.subscribe ({ userResult ->
				onLoading.cancel()
				onHandleInput.onNext(SUCCESS)
				_newUser.postValue(userResult)
			}) { error ->
				onLoading.cancel()
				when (error) {
					is UserNotFound -> onHandleInput.onNext(ERROR_USER)
					else -> onHandleInput.onNext(ERROR_NETWORK)
				}
				Log.d("bestTAG", "ERROR: $error ${error.message}")
			}.addTo(composite)
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