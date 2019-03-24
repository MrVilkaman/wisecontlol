package io.someapp.wisecontlol.ui.screen.taskinfo

import com.arellomobile.mvp.InjectViewState
import io.someapp.wisecontlol.data.category.CategoryEntity
import io.someapp.wisecontlol.data.tasks.TaskFullEntity
import io.someapp.wisecontlol.di.FragmentScope
import io.someapp.wisecontlol.di.SomeId
import io.someapp.wisecontlol.domain.CategoryInteractor
import io.someapp.wisecontlol.domain.TaskInteractor
import io.someapp.wisecontlol.ui.core.BasePresenter
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject


@InjectViewState
@FragmentScope
class TaskInfoPresenter @Inject constructor(
    @SomeId private val taskId: Long?,
    private val editMode: Boolean,
    private val categoryInteractor: CategoryInteractor,
    private val taskInteractor: TaskInteractor
) : BasePresenter<TaskInfoView>() {

    private lateinit var currentTask: TaskFullEntity

    override fun attachView(view: TaskInfoView) {
        super.attachView(view)
        launch {
            if (editMode) {
                if (::currentTask.isInitialized.not()) {
                    currentTask = if (taskId == null) {
                        taskInteractor.createNew()
                    } else {
                        taskInteractor.getTask(taskId)
                    }
                }
            } else {
                currentTask = taskInteractor.getTask(taskId!!)
            }

            viewState.updateUi(currentTask)
        }
    }

    fun onClickCategories() = launch {
        val list = categoryInteractor.getAllCategories()
        viewState.showCategoriesChooseDialog(list)
    }

    fun updateTitle(text: String) {
        currentTask.task.title = text
        viewState.updateUi(currentTask)
    }

    fun save(description: String) {
        if (currentTask.task.title.isBlank()) {
            viewState.showTitleError()
            return
        }

        launch {
            currentTask.task.description = description

            if (taskId == null) {
                taskInteractor.insert(currentTask.task)
            } else {
                taskInteractor.update(currentTask.task)
            }
            router.exit()
        }
    }

    fun close() = router.exit()

    fun onClickCategory(value: CategoryEntity) {
        currentTask.task.categoryId = value.id
        currentTask.category = value
        viewState.updateUi(currentTask)
    }

    fun updateStartDate(time: Date) {
        currentTask.task.startDate = time
        viewState.updateUi(currentTask)
    }

    fun onClickEdit() {
        router.navigateTo(TaskInfoScreenEdit(taskId))
    }

    fun onClickDelete() {
        launch {
            taskInteractor.delete(currentTask.task)
            router.exit()
        }
    }

    fun onClickSend() {
    }
}