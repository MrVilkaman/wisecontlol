package io.someapp.wisecontlol.domain


import io.someapp.wisecontlol.data.category.CategoryEntity
import io.someapp.wisecontlol.data.db.WiseDatabase
import io.someapp.wisecontlol.data.db.category.CategoryDao
import io.someapp.wisecontlol.data.tasks.TaskEntity
import io.someapp.wisecontlol.data.tasks.TaskFullEntity
import io.someapp.wisecontlol.ui.utils.withIO
import javax.inject.Inject

interface TaskInteractor {
    suspend fun getTask(taskId: Long): TaskFullEntity
    suspend fun getAll(): List<TaskFullEntity>
    suspend fun getAllInCategory(categoryId: Long): List<TaskFullEntity>
    suspend fun updateTaskState(id: Long, newState: Boolean)
    suspend fun createNew(): TaskFullEntity
    suspend fun insert(task: TaskEntity)
    suspend fun update(task: TaskEntity)
    suspend fun delete(task: TaskEntity)

}

class TaskInteractorImpl @Inject constructor(
    db: WiseDatabase
) : TaskInteractor {

    private val taskDao by lazy { db.taskDao() }
    private val categoryDao by lazy { db.categoryDao() }


    override suspend fun createNew(): TaskFullEntity = withIO {
        val category = categoryDao.getById(CategoryDao.EMPTY_CATEGORY)
        TaskFullEntity(TaskEntity(), category)
    }

    override suspend fun getTask(taskId: Long): TaskFullEntity = withIO {
        val task = taskDao.getById(taskId)
        val category = loadCategory(task.categoryId)
        TaskFullEntity(task, category)
    }

    private fun loadCategory(categoryId: Long?): CategoryEntity? {
        return if (categoryId != null) {
            categoryDao.getById(categoryId)
        } else {
            categoryDao.getById(CategoryDao.EMPTY_CATEGORY)
        }
    }

    override suspend fun getAll(): List<TaskFullEntity> = withIO {
        taskDao.getAll().map {
            TaskFullEntity(it, loadCategory(it.id))
        }
    }

    override suspend fun getAllInCategory(categoryId: Long): List<TaskFullEntity> {
        return taskDao.getAllInCategory(categoryId).map {
            TaskFullEntity(it, loadCategory(it.id))
        }
    }

    override suspend fun updateTaskState(id: Long, newState: Boolean) = withIO {
        taskDao.updateTaskState(id, newState)
    }

    override suspend fun insert(task: TaskEntity) = withIO {
        taskDao.insert(task)
    }

    override suspend fun update(task: TaskEntity)= withIO {
        taskDao.update(task)
    }

    override suspend fun delete(task: TaskEntity) = withIO {
        taskDao.delete(task)
    }
}