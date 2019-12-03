package com.nalexand.friendlocation.repository

import com.nalexand.friendlocation.base.BaseRepository
import com.nalexand.friendlocation.model.entity.UserEntity
import com.nalexand.friendlocation.model.local.User
import com.nalexand.friendlocation.repository.app.AppPreferences
import com.nalexand.friendlocation.repository.data.dao.UserDao
import io.reactivex.Flowable
import javax.inject.Inject

class IntraRepository @Inject constructor(
	private val preferences: AppPreferences,
	private val userDao: UserDao
) : BaseRepository() {

	private val mapper = DataMapper()

	fun getMembers(): Flowable<List<User>> {
		return prepareFlowable(userDao.getAll())
			.map { mapper.map(it) }
	}

	class DataMapper {

		fun map(userEntities: List<UserEntity>): List<User> {
			return userEntities.map {
				User(it.id, it.login, it.host, it.begin_at, it.end_at)
			}
		}
	}
}