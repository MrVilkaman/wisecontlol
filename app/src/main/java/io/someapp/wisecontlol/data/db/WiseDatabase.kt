package io.someapp.wisecontlol.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import io.someapp.wisecontlol.data.db.task.TaskDao
import io.someapp.wisecontlol.data.tasks.TaskEntity


@Database(entities = [TaskEntity::class], version = 1)
abstract class WiseDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}
