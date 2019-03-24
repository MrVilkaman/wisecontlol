package io.someapp.wisecontlol.data.db.task

import androidx.room.*
import io.someapp.wisecontlol.data.tasks.TaskEntity


@Dao
interface TaskDao {

    @Query("SELECT * FROM TaskEntity")
    fun getAll(): List<TaskEntity>

    @Query("SELECT * FROM TaskEntity WHERE categoryId = :categoryId")
    fun getAllInCategory(categoryId: Long): List<TaskEntity>

    @Query("SELECT * FROM TaskEntity WHERE id = :id")
    fun getById(id: Long): TaskEntity

    @Insert
    fun insert(employee: TaskEntity)

    @Update
    fun update(employee: TaskEntity)

    @Query("UPDATE TaskEntity SET isDone=:isDone WHERE id = :taskId")
    fun updateTaskState(taskId: Long, isDone: Boolean)

    @Delete
    fun delete(employee: TaskEntity)
}