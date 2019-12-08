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
import javax.inject.Inject

class IntraRepository @Inject constructor(
	private val userDao: UserDao,
	private val service: IntraUserService
) : BaseRepository() {

	private val mapper = DataMapper(userDao)

	fun updateLocations(): Single<List<User>> {
		val users = getAllUsersFromDatabase()

		return if (users.isNullOrEmpty()) {
			Single.create { emptyList<User>() }
		} else {
			val query = users.joinToString(",") { it.id }
			service.getUserActiveLocation(query)
				.map {
					Log.d("bestTAG", "123")
					mapper.map(it)
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
		return prepareSingle(service.getUser(login))
			.map {
				if (it.isEmpty()) {
					throw UserNotFound()
				}
				it[0]
			}
			.flatMap { userResponse ->
				prepareSingle(service.getUserLastLocation(userResponse.id))
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