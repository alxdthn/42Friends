package com.nalexand.friendlocation.repository.data.dao

import androidx.room.*
import com.nalexand.friendlocation.base.BaseDao
import com.nalexand.friendlocation.model.entity.UserEntity
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface UserDao : BaseDao<UserEntity> {

	@Query("SELECT * FROM UserEntity ORDER BY end_at ASC")
	fun getAll(): Flowable<List<UserEntity>>

	@Query("SELECT * FROM UserEntity WHERE id LIKE (:id)")
	fun getById(id: String): UserEntity?

	@Query("SELECT * FROM UserEntity WHERE login LIKE :login")
	fun getByLogin(login: String): UserEntity?

}