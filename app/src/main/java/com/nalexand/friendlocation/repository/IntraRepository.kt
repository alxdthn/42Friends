package com.nalexand.friendlocation.repository

import android.util.Log
import com.nalexand.friendlocation.base.BaseRepository
import com.nalexand.friendlocation.errors.UserNotFound
import com.nalexand.friendlocation.model.entity.UserEntity
import com.nalexand.friendlocation.model.local.User
import com.nalexand.friendlocation.model.response.LocationResponse
import com.nalexand.friendlocation.model.response.UserResponse
import com.nalexand.friendlocation.network.service.IntraUserService
import com.nalexand.friendlocation.repository.data.dao.UserDao
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class IntraRepository @Inject constructor(
	private val userDao: UserDao,
	private val service: IntraUserService
) : BaseRepository() {

	private val mapper = DataMapper(userDao)

	fun updateLocations(): Single<List<User>> {
		val users = userDao.getAll()

		return if (users.isNullOrEmpty()) {
			Single.create { emptyList<User>() }
		} else {
			val query = users.joinToString(",") { it.id }
			service.getUserActiveLocation(query)
				.map { locations ->
					users.forEach { user ->
						val location = locations.find { it.user.id == user.id }

						if (location != null) {
							user.begin_at = location.begin_at
							user.end_at = null
							user.host = location.host
						} else {
							user.begin_at = null
							user.host = null
						}
						userDao.update(user)
					}
					getAllUsersFromDatabase()
				}
		}
	}

	fun getAllUsersFromDatabase(): List<User> {
		return mapper.map(userDao.getAll())
	}

	fun getUserByLogin(login: String): User? {
		return mapper.map(userDao.getByLogin(login))
	}

	fun findUserInApi(login: String): Single<User> {
		return service.getUser(login)
			.subscribeOn(Schedulers.io())
			.map {
				if (it.isEmpty()) throw UserNotFound()
				it.first()
			}
			.flatMap { userResponse ->
				service.getUserLastLocation(userResponse.id)
			}
			.map {
				mapper.map(it)
				getUserByLogin(it[0].user.login)
			}
	}

	class DataMapper(private val userDao: UserDao) {

		fun map(locations: Array<LocationResponse>) {
			locations.forEach { location ->
				val userEntity = UserEntity(
					id = location.user.id,
					login = location.user.login
				)
				if (location.end_at == null) {
					userEntity.apply {
						begin_at = location.begin_at
						end_at = location.end_at
						host = location.host
					}
				}
				userDao.insert(userEntity)
			}
		}

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