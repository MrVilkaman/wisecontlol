package io.someapp.wisecontlol.ui.screen.taskinfo

import com.arellomobile.mvp.InjectViewState
import io.someapp.wisecontlol.data.db.WiseDatabase
import io.someapp.wisecontlol.data.tasks.TaskEntity
import io.someapp.wisecontlol.di.FragmentScope
import io.someapp.wisecontlol.di.SomeId
import io.someapp.wisecontlol.ui.core.BasePresenter
import io.someapp.wisecontlol.ui.utils.withIO
import kotlinx.coroutines.launch
import javax.inject.Inject


@InjectViewState
@FragmentScope
class TaskInfoPresenter @Inject constructor(
        @SomeId private val taskId: Long?,
        private val db: WiseDatabase
) : BasePresenter<TaskInfoView>() {

    private lateinit var currentTask: TaskEntity

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        launch {
            currentTask = if (taskId == null) {
                TaskEntity()
            } else {
                withIO { db.taskDao().getById(taskId) }
            }
            viewState.updateUi(currentTask)
        }
    }

    fun updateTitle(text: String) {
        currentTask.title = text
        viewState.updateUi(currentTask)
    }

    fun save(description: String) {
        launch {
            currentTask.description = description

            val taskDao = db.taskDao()
            withIO {
                if (taskId == null) {
                    taskDao.insert(currentTask)
                } else {
                    taskDao.update(currentTask)
                }
            }
            router.exit()
        }
    }

    fun close() = router.exit()

}