package com.nalexand.friendlocation.data

import androidx.room.*

@Dao
interface AppDao {
	@Query("SELECT * FROM UserLocationEntity ORDER BY end_at DESC")
	fun getAll(): MutableList<UserLocationEntity>

	@Query("SELECT * FROM UserLocationEntity WHERE user_id LIKE (:user_id)")
	fun getById(user_id: Long): UserLocationEntity

	@Query("SELECT * FROM UserLocationEntity WHERE login LIKE :login")
	fun getByLogin(login: String): UserLocationEntity

	@Query("SELECT * FROM Token")
	fun getToken(): Token

	@Query("SELECT COUNT(login) FROM UserLocationEntity")
	fun getCount() : Int

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	fun insert(userEntity: UserLocationEntity)

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	fun insertToken(token: Token)

	@Update
	fun update(userEntity: UserLocationEntity)

	@Delete
	fun delete(userEntity: UserLocationEntity)
}