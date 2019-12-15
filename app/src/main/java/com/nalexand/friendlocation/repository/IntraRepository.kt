package com.nalexand.friendlocation.repository

import com.nalexand.friendlocation.base.BaseRepository
import com.nalexand.friendlocation.errors.UserNotFound
import com.nalexand.friendlocation.model.local.User
import com.nalexand.friendlocation.network.service.IntraUserService
import com.nalexand.friendlocation.repository.data.dao.UserDao
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class IntraRepository @Inject constructor(
	private val userDao: UserDao,
	private val service: IntraUserService,
	private val mapper: DataMapper
) : BaseRepository() {

	fun updateLocations(): Single<List<User>> {
		val users = userDao.getAll()

		return if (users.isNullOrEmpty()) {
			Single.create { emptyList<User>() }
		} else {
			val query = users.joinToString(",") { it.id }
			service.getUserActiveLocation(query)
				.subscribeOn(Schedulers.io())
				.map { locations ->
					mapper.map(users, locations)
					getAllUsersFromDatabase()
				}
		}
	}

	fun getAllUsersFromDatabase(): List<User> {
		return mapper.map(userDao.getAll())
	}

	fun getUserFromDatabaseByLogin(login: String): User? {
		return mapper.map(userDao.getByLogin(login))
	}

	fun getUserFromDatabaseById(idUser: String): User? {
		return mapper.map(userDao.getById(idUser))
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
				getUserFromDatabaseByLogin(it[0].user.login)
			}
	}
}