package io.someapp.wisecontlol.ui.screen.main

import com.arellomobile.mvp.InjectViewState
import io.someapp.wisecontlol.di.FragmentScope
import io.someapp.wisecontlol.ui.core.BasePresenter
import io.someapp.wisecontlol.ui.screen.taskinfo.TaskInfoScreen
import io.someapp.wisecontlol.ui.screen.tasks.TasksScreen
import javax.inject.Inject


@InjectViewState
@FragmentScope
class MainPresenter @Inject constructor(
) : BasePresenter<MainView>() {
    fun onClickTasks() {
        router.navigateTo(TasksScreen)
    }

    fun onClickAddTask() {
        router.navigateTo(TaskInfoScreen())
    }
}