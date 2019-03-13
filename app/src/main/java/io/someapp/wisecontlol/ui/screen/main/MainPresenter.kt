package io.someapp.wisecontlol.ui.screen.main

import com.arellomobile.mvp.InjectViewState
import io.someapp.wisecontlol.data.db.WiseDatabase
import io.someapp.wisecontlol.data.tasks.TaskEntity
import io.someapp.wisecontlol.di.FragmentScope
import io.someapp.wisecontlol.ui.core.BasePresenter
import io.someapp.wisecontlol.ui.screen.tasks.TasksScreen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject


@InjectViewState
@FragmentScope
class MainPresenter @Inject constructor(
    private val db: WiseDatabase

) : BasePresenter<MainView>() {
    fun onClickTasks() {
        router.navigateTo(TasksScreen)
    }

    fun onClickAddTask() {
        launch {
            withContext(Dispatchers.IO) {
                db.taskDao().insert(TaskEntity().apply { title = UUID.randomUUID().toString() })
            }
            viewState.success()
        }

//        router.navigateTo(TaskInfoScreen)
    }
}