package com.nalexand.friendlocation.repository

import com.nalexand.friendlocation.model.entity.UserEntity
import com.nalexand.friendlocation.model.local.User
import com.nalexand.friendlocation.model.response.LocationResponse
import com.nalexand.friendlocation.model.response.UserResponse
import com.nalexand.friendlocation.repository.data.dao.UserDao
import javax.inject.Inject

class DataMapper @Inject constructor(private val userDao: UserDao) {

	fun map(userEntities: List<UserEntity>, locations: Array<LocationResponse>) {
		userEntities.forEach { user ->
			val location = locations.find { it.user.id == user.id }

			user.apply {
				if (location != null) {
					begin_at = location.begin_at
					end_at = null
					host = location.host
				} else {
					begin_at = null
					host = null
				}
				userDao.update(this)
			}
		}
	}

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