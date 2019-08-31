package com.example.getfriendlocation.data

import androidx.room.*

@Dao
interface UsersDao {
	@Query("SELECT * FROM UserLocationEntity ORDER BY end_at ASC")
	fun getAll(): MutableList<UserLocationEntity>

	@Query("SELECT * FROM UserLocationEntity WHERE user_id LIKE (:user_id)")
	fun getById(user_id: Long): UserLocationEntity

	@Query("SELECT * FROM UserLocationEntity WHERE login LIKE :login")
	fun getByLogin(login: String): UserLocationEntity

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	fun insert(userEntity: UserLocationEntity)

	@Update
	fun update(userEntity: UserLocationEntity)

	@Delete
	fun delete(userEntity: UserLocationEntity)
}