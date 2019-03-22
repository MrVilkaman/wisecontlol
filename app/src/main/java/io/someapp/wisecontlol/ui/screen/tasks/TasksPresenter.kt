package io.someapp.wisecontlol.ui.screen.tasks

import com.arellomobile.mvp.InjectViewState
import io.someapp.wisecontlol.data.db.WiseDatabase
import io.someapp.wisecontlol.data.tasks.TaskEntity
import io.someapp.wisecontlol.di.FragmentScope
import io.someapp.wisecontlol.ui.core.BasePresenter
import io.someapp.wisecontlol.ui.screen.taskinfo.TaskInfoScreen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@InjectViewState
@FragmentScope
class TasksPresenter @Inject constructor(
        private val db: WiseDatabase
) : BasePresenter<TasksView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        launch {
            val list = withContext(Dispatchers.IO) { db.taskDao().getAll() }

            viewState.bindItems(list)
        }

    }

    fun onClickTask(value: TaskEntity) {
        router.navigateTo(TaskInfoScreen(value.id))
    }
}