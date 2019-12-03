package com.nalexand.friendlocation.repository

import com.nalexand.friendlocation.data.AppDatabase
import com.nalexand.friendlocation.model.local.Member
import javax.inject.Inject

class IntraRepository @Inject constructor(
	private val preferences: AppPreferences,
	private val database: AppDatabase
) {

	fun getMembers(): List<Member> {
		return database.make().getAllMembers()
	}
}