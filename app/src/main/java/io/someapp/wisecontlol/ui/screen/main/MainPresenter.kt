package io.someapp.wisecontlol.ui.screen.main

import com.arellomobile.mvp.InjectViewState
import io.someapp.wisecontlol.BuildConfig
import io.someapp.wisecontlol.R
import io.someapp.wisecontlol.data.category.CategoryEntity
import io.someapp.wisecontlol.di.FragmentScope
import io.someapp.wisecontlol.domain.CategoryInteractor
import io.someapp.wisecontlol.domain.NotificationsSettingsManager
import io.someapp.wisecontlol.ui.core.BasePresenter
import io.someapp.wisecontlol.ui.screen.taskinfo.TaskInfoScreenEdit
import io.someapp.wisecontlol.ui.screen.tasks.TasksScreen
import io.someapp.wisecontlol.ui.utils.Date.Companion.todayNight
import kotlinx.coroutines.launch
import javax.inject.Inject


@InjectViewState
@FragmentScope
class MainPresenter @Inject constructor(
    private val categoryInteractor: CategoryInteractor,
    private val notif: NotificationsSettingsManager
) : BasePresenter<MainView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        notif.restartNotifications()
    }

    fun onClickTasks() {
        router.navigateTo(TasksScreen())
    }

    fun onClickAddTask() {
        router.navigateTo(TaskInfoScreenEdit())
    }

    fun onClickCategory(value: CategoryEntity) {
        router.navigateTo(TasksScreen(value))
    }

    fun onClickTaskOfDays() {
        val tasksScreen = TasksScreen(day = todayNight())
        router.navigateTo(tasksScreen)
    }

    fun onClickChaos() {
        val tasksScreen = TasksScreen(withoutDate = true)
        router.navigateTo(tasksScreen)
    }

    fun onClickCategories() = launch {
        val list = categoryInteractor.getAllCategories()
        viewState.showCategoriesChooseDialog(list)
    }

    fun tryAddCategory(name: String) = launch {
        try {
            categoryInteractor.addCategory(name)
            viewState.showToast(R.string.category_added_done)
        } catch (e: Exception) {
            when (e) {
                is IllegalArgumentException -> viewState.showToast(R.string.category_exist)
                else -> {
                    if (BuildConfig.DEBUG) {
                        throw e
                    } else {
                        viewState.showToast(R.string.some_error)
                    }
                }
            }
        }

    }
}