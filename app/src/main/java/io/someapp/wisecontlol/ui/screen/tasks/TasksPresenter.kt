package io.someapp.wisecontlol.ui.screen.tasks

import com.arellomobile.mvp.InjectViewState
import io.someapp.wisecontlol.data.tasks.TaskFullEntity
import io.someapp.wisecontlol.di.FragmentScope
import io.someapp.wisecontlol.domain.TaskInteractor
import io.someapp.wisecontlol.ui.core.BasePresenter
import io.someapp.wisecontlol.ui.screen.taskinfo.TaskInfoScreen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject


@InjectViewState
@FragmentScope
class TasksPresenter @Inject constructor(
    private val param: TasksScreenParam,
    private val taskInteractor: TaskInteractor
) : BasePresenter<TasksView>() {

    private fun updateList() {
        launch {
            val list = withContext(Dispatchers.IO) {
                with(param) {
                    return@with when {
                        categoryId != null -> taskInteractor.getAllInCategory(categoryId)
                        day != null -> taskInteractor.getAllWithDate(day)
                        withoutDate == true -> taskInteractor.getAllWithDate(null)

                        else -> taskInteractor.getAll()
                    }

                }.sortedWith(compareBy({ it.task.isDone }, { it.task.startDate }))
            }
            viewState.bindItems(list)
        }
    }

    override fun attachView(view: TasksView?) {
        super.attachView(view)
        updateList()
    }

    fun onClickTask(value: TaskFullEntity) {
        router.navigateTo(TaskInfoScreen(value.task.id))
    }

    fun onCheckedTask(id: Long, newState: Boolean) {
        launch {
            taskInteractor.updateTaskState(id, newState)
            updateList()
        }
    }

    private val currentDate = Date()

    fun beforeDate(startDate: Date?): Boolean = startDate?.before(currentDate) == true
}