package io.someapp.wisecontlol.data.db.task

import androidx.room.*
import io.someapp.wisecontlol.data.tasks.TaskEntity


@Dao
interface TaskDao {

    @Query("SELECT * FROM TaskEntity")
    fun getAll(): List<TaskEntity>

    @Query("SELECT * FROM TaskEntity WHERE id = :id")
    fun getById(id: Long): TaskEntity

    @Insert
    fun insert(employee: TaskEntity)

    @Update
    fun update(employee: TaskEntity)

    @Delete
    fun delete(employee: TaskEntity)
}