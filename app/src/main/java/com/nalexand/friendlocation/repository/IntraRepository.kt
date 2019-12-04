package com.nalexand.friendlocation.repository

import com.nalexand.friendlocation.base.BaseRepository
import com.nalexand.friendlocation.model.entity.UserEntity
import com.nalexand.friendlocation.model.local.User
import com.nalexand.friendlocation.model.response.UserResponse
import com.nalexand.friendlocation.network.service.IntraUserService
import com.nalexand.friendlocation.repository.data.dao.UserDao
import com.nalexand.friendlocation.ui.add_user.AddUserViewModel.Companion.ERROR_USER
import com.nalexand.friendlocation.ui.add_user.AddUserViewModel.Companion.SUCCESS
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

class IntraRepository @Inject constructor(
	private val userDao: UserDao,
	private val service: IntraUserService
) : BaseRepository() {

	private val mapper = DataMapper()

	fun getAllUsersFromDatabase(): Flowable<List<User>> {
		return prepareFlowable(userDao.getAll())
			.map { mapper.map(it) }
	}

	fun getUserByLogin(login: String): User? {
		return mapper.map(userDao.getByLogin(login))
	}

	fun findUserInApi(login: String): Single<Int> {
		return prepareSingle(service.getUser(login))
			.map {
				return@map if (it.isEmpty()) ERROR_USER
				else {
					userDao.insert(mapper.map(it[0]))
					SUCCESS
				}
			}
	}

	class DataMapper {

		fun map(userResponse: UserResponse): UserEntity {
			return userResponse.run {
				UserEntity(id, login)
			}
		}

		fun map(userEntities: List<UserEntity>): List<User> {
			return userEntities.map { map(it) ?: throw IllegalStateException() }
		}

		fun map(userEntity: UserEntity?): User? {
			if (userEntity == null) return null
			return userEntity.run {
				User(id, login, host, begin_at, end_at)
			}
		}
	}
}